package com.rasalexman.sticky.core

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

typealias StickyBlock<V, R> = V.(StickyContinuation<R>) -> Unit
typealias ViewContinuations<V> = MutableList<Continuation<V>>
typealias StickyContinuations<V> = MutableMap<StickyContinuation<*>, StickyBlock<V, *>>

abstract class BaseStickyPresenter<V : IStickyView> : IStickyPresenter<V> {
    override var unsafeView: V? = null

    open val viewAvailableState: Lifecycle.State = Lifecycle.State.RESUMED

    private var viewLifecycle: Lifecycle? = null
    private val isViewAvailable = AtomicBoolean(true)
    private val viewContinuations: ViewContinuations<V> = mutableListOf()
    private val stickyContinuations: StickyContinuations<V> = mutableMapOf()
    private var mustRestoreStickyContinuations: Boolean = false

    @Synchronized
    suspend fun view(): V {
        if (isViewAvailable.get()) {
            unsafeView?.let { return it }
        }

        // We wait until the unsafeView is ready to be used again
        return suspendCoroutine { continuation -> viewContinuations.add(continuation) }
    }

    @Synchronized
    override fun attachView(view: V, viewLifecycle: Lifecycle) {
        this.unsafeView = view
        this.viewLifecycle = viewLifecycle
        onViewAttached(view)
        viewLifecycle.addObserver(this)
    }

    protected open fun onViewAttached(view: V) = Unit

    @Synchronized
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    private fun onViewStateChanged() {
        val isViewReady = viewLifecycle?.currentState?.isAtLeast(Lifecycle.State.RESUMED) ?: false
        isViewAvailable.set(isViewReady)
    }

    @Synchronized
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onViewReadyForContinuations() {
        val viewInstance = this.unsafeView
        if (viewInstance != null) {
            val viewContinuationsIterator = viewContinuations.listIterator()

            while (viewContinuationsIterator.hasNext()) {
                val continuation = viewContinuationsIterator.next()

                // The unsafeView was not ready when the presenter needed it earlier,
                // but now it's ready again so the presenter can continue
                // interacting with it.
                viewContinuationsIterator.remove()
                continuation.resume(viewInstance)
            }
        }
    }

    @Synchronized
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onViewReadyForStickyContinuations() {
        val viewInstance = this.unsafeView
        if (viewInstance != null) {
            if (mustRestoreStickyContinuations) {
                mustRestoreStickyContinuations = false

                val stickyContinuationsIterator = stickyContinuations.iterator()

                while (stickyContinuationsIterator.hasNext()) {
                    val stickyContinuationBlockMap = stickyContinuationsIterator.next()
                    val stickyContinuation = stickyContinuationBlockMap.key
                    val stickyContinuationBlock = stickyContinuationBlockMap.value
                    viewInstance.stickyContinuationBlock(stickyContinuation)
                    stickyContinuation.checkStickyState()
                }
            }
        }
    }

    @Synchronized
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected open fun onViewDestroyed() {
        unsafeView = null
        this.viewLifecycle?.removeObserver(this)
        viewLifecycle = null
        mustRestoreStickyContinuations = true
    }

    fun onCleared() {
        cleanup()
    }

    @Synchronized
    private fun addStickyContinuation(
        continuation: StickyContinuation<*>,
        block: StickyBlock<V, *>
    ) {
        stickyContinuations[continuation] = block
    }

    @Synchronized
    override fun removeStickyContinuation(continuation: StickyContinuation<*>): Boolean {
        return stickyContinuations.remove(continuation)?.let {
            continuation.clear()
            true
        } ?: false
    }

    /**
     * Executes the given block on the unsafeView. The block is executed again
     * every time the unsafeView instance changes and the new unsafeView is resumed.
     * This, for example, is useful for dialogs that need to be persisted
     * across orientation changes.
     *
     * @param block code that has to be executed on the unsafeView
     */
    @Suppress("UNCHECKED_CAST")
    suspend fun <ReturnType> V.stickySuspension(
        strategy: StickyStrategy = StickyStrategy.Many,
        block: StickyBlock<V, ReturnType>
    ): ReturnType {
        return suspendCoroutine { continuation ->
            val stickyContinuation: StickyContinuation<ReturnType> =
                    StickyContinuation(continuation, this@BaseStickyPresenter, strategy)
            addStickyContinuation(stickyContinuation, block as V.(StickyContinuation<*>) -> Unit)
            block(stickyContinuation).also {
                stickyContinuation.checkStickyState()
            }
        }
    }

    @Synchronized
    open fun cleanup() {
        viewContinuations.clear()
        stickyContinuations.clear()
    }

    private fun <ReturnType> StickyContinuation<ReturnType>.checkStickyState() {
        when (this.strategy) {
            is StickyStrategy.Single -> removeStickyContinuation(this)
            is StickyStrategy.Counter -> this.increaseCounter()
            is StickyStrategy.Many -> Unit
        }
    }
}
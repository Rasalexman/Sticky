package com.rasalexman.sticky.core

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.*

typealias ViewContinuations<V> = MutableList<Continuation<V>>
typealias StickyList<V> = MutableList<BaseSticky<V>>

@Suppress("UNCHECKED_CAST")
abstract class BaseStickyPresenter<V : IStickyView> : IStickyPresenter<V> {
    override var unsafeView: V? = null

    open val viewAvailableState: Lifecycle.State = Lifecycle.State.RESUMED

    private var viewLifecycle: Lifecycle? = null
    private val isViewAvailable = AtomicBoolean(true)

    private val viewContinuations: ViewContinuations<V> = mutableListOf()
    private val stickyList: StickyList<V> = mutableListOf()

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

                val stickiesIterator = stickyList.listIterator()
                while (stickiesIterator.hasNext()) {
                    val sticky = stickiesIterator.next()
                    sticky.resume(viewInstance)
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

    fun V.sticky(
        strategy: StickyStrategy = StickyStrategy.Many,
        stickyContext: CoroutineContext = EmptyCoroutineContext,
        block: StickyBlock<V>
    ): BaseSticky<V> {
        return Sticky(strategy, stickyContext, block).apply {
            removerCallback = ::removeSticky
            addSticky(this)
            resume(this@sticky)
        }
    }

    fun V.sticky(
        stickyContext: CoroutineContext = EmptyCoroutineContext,
        block: StickyBlock<V>
    ) = sticky(StickyStrategy.Many, stickyContext, block)

    @ExperimentalUnsignedTypes
    fun V.sticky(
        executionCount: UInt,
        stickyContext: CoroutineContext = EmptyCoroutineContext,
        block: StickyBlock<V>
    ) = sticky(StickyStrategy.Counter(executionCount), stickyContext, block)

    fun V.singleSticky(
        stickyContext: CoroutineContext = EmptyCoroutineContext,
        block: StickyBlock<V>
    ) = sticky(StickyStrategy.Single, stickyContext, block)

    @Synchronized
    private fun removeSticky(sticky: BaseSticky<V>) {
        stickyList.remove(sticky)
    }

    @Synchronized
    private fun addSticky(sticky: BaseSticky<V>) {
        stickyList.add(sticky)
    }

    @Synchronized
    open fun cleanup() {
        viewContinuations.clear()
        stickyList.clear()
    }
}
package com.rasalexman.sticky.core

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.rasalexman.sticky.core.sticky.*
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Suppress("UNCHECKED_CAST")
interface IStickyPresenter<V : IStickyView> : LifecycleObserver {

    private val viewLifecycle: Lifecycle?
        get() = unsafeView?.getLifecycle()

    private val isViewAvailable: AtomicBoolean
        get() = viewAvailableMap.getOrPut(this) { AtomicBoolean(true) }

    private val viewContinuations: ViewContinuationsList<V>
        get() = viewContinuationsMap.getOrPut(this) { mutableListOf() } as ViewContinuationsList<V>

    private val stickyList: ViewStickyList<V>
        get() = viewStickiesMap.getOrPut(this) { mutableListOf() } as ViewStickyList<V>

    private var unsafeView: V?
        get() = viewLifecyclerMap[this] as? V
        set(value) {
            viewLifecyclerMap[this] = value
        }

    private var mustRestoreSticky: Boolean
        get() = viewRestoreStickiesMap.getOrPut(this) { false }
        set(value) {
            viewRestoreStickiesMap[this] = value
        }

    fun attach(view: IStickyView) {
        (view as? V)?.let { castedView ->
            unsafeView = castedView
            onViewAttached(castedView)
            castedView.getLifecycle().addObserver(this)
        }
    }

    fun onViewAttached(view: V) = Unit

    @Synchronized
    suspend fun <V : IStickyView> IStickyPresenter<V>.view(): V {
        if (isViewAvailable.get()) {
            unsafeView?.let { return it }
        }
        // We wait until the unsafeView is ready to be used again
        return suspendCoroutine { continuation -> viewContinuations.add(continuation) }
    }

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
            if (mustRestoreSticky) {
                mustRestoreSticky = false

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
    open fun onViewDestroyed() {
        this.viewLifecycle?.removeObserver(this)
        unsafeView = null
        mustRestoreSticky = true
    }

    fun V.sticky(
        strategy: StickyStrategy = StickyStrategy.Many,
        stickyContext: CoroutineContext = EmptyCoroutineContext,
        block: StickyBlock<V>
    ): ISticky<V> {
        return Sticky(
            stickyStrategy = strategy,
            context = stickyContext,
            stickyBlock = block,
            removerCallback = ::removeSticky
        ).apply {
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
    private fun removeSticky(sticky: ISticky<V>) {
        stickyList.remove(sticky)
    }

    @Synchronized
    private fun addSticky(sticky: ISticky<V>) {
        stickyList.add(sticky)
    }

    @Synchronized
    fun <V : IStickyView> IStickyPresenter<V>.cleanup() {
        viewContinuations.clear()
        stickyList.clear()

        viewLifecyclerMap.remove(this)
        viewAvailableMap.remove(this)
        viewRestoreStickiesMap.remove(this)
        viewContinuationsMap.remove(this)?.clear()
        viewStickiesMap.remove(this)?.clear()

    }

    companion object {
        private val viewLifecyclerMap: ViewLifecyclerMap = mutableMapOf()
        private val viewAvailableMap: ViewAvailableMap = mutableMapOf()
        private val viewContinuationsMap: ViewContinuationMap = mutableMapOf()
        private val viewStickiesMap: ViewStickiesMap = mutableMapOf()
        private val viewRestoreStickiesMap: ViewRestoreStickyMap = mutableMapOf()
    }
}


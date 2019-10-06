// Copyright (c) 2019 Aleksandr Minkin (sphc@yandex.ru)
//
// Permission is hereby granted, free of charge, to any person obtaining a copy of this software
// and associated documentation files (the "Software"), to deal in the Software without restriction,
// including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
// and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
// subject to the following conditions:
// The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
// WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
// IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
// WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
// THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
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

/**
 * Base interface to communicate and manipulate [IStickyView]
 */
@Suppress("UNCHECKED_CAST")
interface IStickyPresenter<V : IStickyView> : LifecycleObserver {

    /**
     * [IStickyView] optional instance
     * You can override get() method
     */
    val unsafeView: V?
        get() = viewLifecyclerMap[this] as? V

    /**
     * Flag that says must restore sticky continuation when [Lifecycle.State.RESUMED] is fired from [IStickyView] instance
     * You can override get() method to restore your sticky actions every time your [View] instance is destroyed
     */
    val mustRestoreSticky: Boolean
        get() = viewRestoreStickiesMap.getOrPut(this) { false }

    /**
     * Base [Lifecycle] instance for manipulate restore of [unsafeView] or [Sticky] continuations
     */
    private val viewLifecycle: Lifecycle?
        get() = unsafeView?.lifecycle

    /**
     * Private
     * Is [unsafeView] is now available for iterate
     */
    private val isViewAvailable: AtomicBoolean
        get() = viewAvailableMap.getOrPut(this) { AtomicBoolean(false) }

    /**
     * Private
     * [MutableList] of [IStickyView] continuations
     */
    private val viewContinuations: ViewContinuationsList<V>
        get() = viewContinuationsMap.getOrPut(this) { mutableListOf() } as ViewContinuationsList<V>

    /**
     * Private
     * [MutableList] of [ISticky] continuations
     */
    private val stickyList: ViewStickyList<V>
        get() = viewStickiesMap.getOrPut(this) { mutableListOf() } as ViewStickyList<V>

    /**
     *
     */
    fun onFirstAttach(view: IStickyView) {
        (view as? V)?.let { castedView ->
            saveCastedView(castedView)
            onViewCreated(castedView)
        }
    }

    /**
     * Attach base [IStickyView] instance and start to observe lifecycle events
     */
    fun onAttach(view: IStickyView) {
        (view as? V)?.let { castedView ->
            saveCastedView(castedView)
            onViewAttached(castedView)
        }
    }

    private fun saveCastedView(view: V) {
        viewLifecyclerMap[this] = view
        view.lifecycle.addObserver(this)
    }

    /**
     * On [IStickyView] was created and casted to [unsafeView]
     */
    fun onViewCreated(view: V) = Unit

    /**
     *
     */
    fun onViewAttached(view: V) = Unit

    /**
     * On [IStickyView] was destroyed (Fragment, View or custom class)
     */
    fun onViewDestroyed(view: V) = Unit

    /**
     * [IStickyView] continuation instance
     * It's always waiting until your [IStickyView] will be available
     */
    @Synchronized
    suspend fun <V : IStickyView> IStickyPresenter<V>.view(): V {
        if (isViewAvailable.get()) {
            unsafeView?.let { return it }
        }
        // We wait until the unsafeView is ready to be used again
        return suspendCoroutine { continuation -> viewContinuations.add(continuation) }
    }

    /**
     * Observer method to check for [unsafeView] is available for manipulations
     */
    @Synchronized
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onViewStateChanged() {
        val state = viewLifecycle?.currentState
        val isViewReady = state?.isAtLeast(Lifecycle.State.RESUMED) ?: false
        isViewAvailable.set(isViewReady)
    }

    /**
     * Observer method to check for [unsafeView] apply sticky and continuations
     */
    @Synchronized
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onViewResumedForContinuations() {
        runContinuationsWithView()
    }

    /**
     * Observer method to check for [unsafeView] to be cleared
     */
    @Synchronized
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onViewDestroyed() {
        this.viewLifecycle?.removeObserver(this)
        unsafeView?.let { onViewDestroyed(it) }
        viewLifecyclerMap[this] = null
        viewRestoreStickiesMap[this] = true
    }

    /**
     * Run continuations with [IStickyView]
     */
    private fun runContinuationsWithView() {
        val viewInstance = this.unsafeView
        viewInstance?.run {
            iterateWithViewContinuations(this)
            iterateWithStickyContinuations(this)
        }
    }

    /**
     * Private
     * Run continuations with [IStickyView]
     */
    private fun iterateWithViewContinuations(viewInstance: V) {
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

    /**
     * Private
     * Run continuations with [ISticky] on [IStickyView]
     */
    private fun iterateWithStickyContinuations(viewInstance: V) {
        if (mustRestoreSticky) {
            viewRestoreStickiesMap[this] = false
            val scopeStickyList = stickyList.toList()
            scopeStickyList.forEach { sticky ->
                sticky.resume(viewInstance)
            }
        }
    }

    /**
     * Base [ISticky] instance creations
     * @param strategy - [StickyStrategy] for apply
     * @param stickyContext - [CoroutineContext] for sticky
     * @param block - [StickyBlock] for be applied
     */
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

    /**
     * Base [ISticky] instance creations with infinite [StickyStrategy.Many] strategy
     * @param stickyContext - [CoroutineContext] for sticky
     * @param block - [StickyBlock] for be applied
     */
    fun V.sticky(
        stickyContext: CoroutineContext = EmptyCoroutineContext,
        block: StickyBlock<V>
    ) = sticky(StickyStrategy.Many, stickyContext, block)

    /**
     * Base [ISticky] instance creations
     * @param executionCount - count of sticky executions
     * @param stickyContext - [CoroutineContext] for sticky
     * @param block - [StickyBlock] for be applied
     */
    @ExperimentalUnsignedTypes
    fun V.sticky(
        executionCount: UInt,
        stickyContext: CoroutineContext = EmptyCoroutineContext,
        block: StickyBlock<V>
    ) = sticky(StickyStrategy.Counter(executionCount), stickyContext, block)

    /**
     * Base [ISticky] single instance creations with [StickyStrategy.Single]
     * @param stickyContext - [CoroutineContext] for sticky
     * @param block - [StickyBlock] for be applied
     */
    fun V.singleSticky(
        stickyContext: CoroutineContext = EmptyCoroutineContext,
        block: StickyBlock<V>
    ) = sticky(StickyStrategy.Single, stickyContext, block)

    /**
     * Private
     * Remove sticky from list
     */
    @Synchronized
    private fun removeSticky(sticky: ISticky<V>) {
        stickyList.remove(sticky)
    }

    /**
     * Private
     * Add sticky to list
     */
    @Synchronized
    private fun addSticky(sticky: ISticky<V>) {
        stickyList.add(sticky)
    }

    /**
     * Private
     * Clear all instances and continuations
     */
    @Synchronized
    fun <V : IStickyView> IStickyPresenter<V>.cleanup() {
        viewLifecyclerMap.remove(this)
        viewAvailableMap.remove(this)
        viewRestoreStickiesMap.remove(this)
        viewContinuationsMap.remove(this)?.clear()
        viewStickiesMap.remove(this)?.clear()
    }

    private companion object {
        private val viewLifecyclerMap: ViewLifecyclerMap = mutableMapOf()
        private val viewAvailableMap: ViewAvailableMap = mutableMapOf()
        private val viewContinuationsMap: ViewContinuationMap = mutableMapOf()
        private val viewStickiesMap: ViewStickiesMap = mutableMapOf()
        private val viewRestoreStickiesMap: ViewRestoreStickyMap = mutableMapOf()
    }
}


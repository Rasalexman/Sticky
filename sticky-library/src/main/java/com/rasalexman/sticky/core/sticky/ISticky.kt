package com.rasalexman.sticky.core.sticky

import com.rasalexman.sticky.common.clear
import com.rasalexman.sticky.core.IStickyView
import kotlin.coroutines.Continuation

/**
 *
 */
interface ISticky<V : IStickyView> : Continuation<V> {

    /**
     *
     */
    val stickyStrategy: StickyStrategy
        get() = StickyStrategy.Many

    /**
     *
     */
    var stickyBlock: StickyBlock<V>?

    /**
     *
     */
    var removerCallback: StickyRemover<V>?

    /**
     *
     */
    var exception: Throwable?

    /**
     *
     */
    var counter: UInt

    /**
     *
     */
    fun<V : IStickyView> ISticky<V>.checkState() {
        when (val localStrategy = this.stickyStrategy) {
            is StickyStrategy.Single -> removerCallback?.invoke(this).also { clear() }
            is StickyStrategy.Counter -> {
                ++counter
                val maxCountOfExecution = localStrategy.maxExecutionCounter
                if(maxCountOfExecution == counter) {
                    clear()
                }
            }
            is StickyStrategy.Many -> Unit
        }
    }
}
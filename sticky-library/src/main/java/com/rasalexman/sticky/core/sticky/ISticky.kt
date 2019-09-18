package com.rasalexman.sticky.core.sticky

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
            is StickyStrategy.Single -> removerCallback?.invoke(this).also { clearSticky() }
            is StickyStrategy.Counter -> {
                ++counter
                val maxCountOfExecution = localStrategy.maxExecutionCounter
                if(maxCountOfExecution == counter) {
                    clearSticky()
                }
            }
            is StickyStrategy.Many -> Unit
        }
    }

    /**
     *
     */
    fun <V : IStickyView> ISticky<V>.clearSticky() {
        removerCallback?.invoke(this)
        removerCallback = null
        exception = null
        stickyBlock = null
    }
}




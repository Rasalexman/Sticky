package com.rasalexman.sticky.core

import kotlin.coroutines.Continuation

abstract class BaseSticky<V>(
    stickyStrategy: StickyStrategy = StickyStrategy.Many
) : Continuation<V> {

    /**
     *
     */
    private val localStrategy: StickyStrategy = stickyStrategy

    /**
     *
     */
    open var removerCallback: ((BaseSticky<V>) -> Unit)? = null

    /**
     * possible results
     */
    protected var exception: Throwable? = null

    /**
     * Counter of how this sticky must be execute
     */
    @ExperimentalUnsignedTypes
    private var _counter: UInt = 0u

    /**
     *
     */
    @ExperimentalUnsignedTypes
    protected fun checkState() {
        when (this.localStrategy) {
            is StickyStrategy.Single -> removerCallback?.invoke(this).also { clearSticky() }
            is StickyStrategy.Counter -> {
                ++_counter
                val maxCountOfExecution = localStrategy.maxExecutionCounter
                if(maxCountOfExecution == _counter) {
                    clearSticky()
                }
            }
            is StickyStrategy.Many -> Unit
        }
    }

    /**
     * Clear presenter instance
     */
    open fun clearSticky() {
        removerCallback?.invoke(this)
        removerCallback = null
        exception = null
    }
}

inline fun<reified V, reified S : BaseSticky<V>> S.clear() {
    this.clearSticky()
}
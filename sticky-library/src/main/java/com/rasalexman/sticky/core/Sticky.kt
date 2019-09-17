package com.rasalexman.sticky.core

import com.rasalexman.sticky.common.StickyException
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.resumeWithException

typealias StickyBlock<V> = V.(BaseSticky<V>) -> Unit

data class Sticky<V>(
    private val strategy: StickyStrategy = StickyStrategy.Many,
    override val context: CoroutineContext = EmptyCoroutineContext,
    private var stickyBlock: StickyBlock<V>? = null
) : BaseSticky<V>(strategy) {

    /**
     *
     */
    @ExperimentalUnsignedTypes
    override fun resumeWith(result: Result<V>) {
        when {
            result.isSuccess -> {
                stickyBlock?.let { block ->
                    result.getOrNull()?.block(this) ?: this.resumeWithException(StickyException.ReceiverClassException())
                } ?: this.resumeWithException(StickyException.ExecutionBlockException())
            }
            result.isFailure -> {
                result.exceptionOrNull()?.let {
                    exception = it
                    this.resumeWithException(StickyException.UncheckedException(it.message))
                } ?: this.resumeWithException(StickyException.UncheckedException())
            }
        }
        checkState()
    }

    override fun clearSticky() {
        super.clearSticky()
        stickyBlock = null
    }
}
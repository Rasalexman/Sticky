package com.rasalexman.sticky.core.sticky

import com.rasalexman.sticky.common.StickyException
import com.rasalexman.sticky.core.IStickyView
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.resumeWithException

data class Sticky<V : IStickyView>(
    override val stickyStrategy: StickyStrategy = StickyStrategy.Many,
    override val context: CoroutineContext = EmptyCoroutineContext,
    override var stickyBlock: StickyBlock<V>? = null,
    override var removerCallback: StickyRemover<V>? = null
) : ISticky<V> {

    override var exception: Throwable? = null
    override var counter: UInt = 0u

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
}
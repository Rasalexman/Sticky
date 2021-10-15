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
package com.rasalexman.sticky.core.sticky

import com.rasalexman.sticky.common.StickyException
import com.rasalexman.sticky.core.IStickyView
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.resumeWithException

/**
 * Base Sticky Implementation
 * @param stickyStrategy - [StickyStrategy]
 * @param context - sticky [CoroutineContext]
 * @param stickyBlock - [StickyBlock] for execute
 * @param removerCallback - [StickyRemover] for execute
 */
data class Sticky<V : IStickyView>(
    override val stickyStrategy: StickyStrategy = StickyStrategy.Many,
    override val context: CoroutineContext = EmptyCoroutineContext,
    override var stickyBlock: StickyBlock<V>? = null,
    override var removerCallback: StickyRemover<V>? = null
) : ISticky<V> {

    /**
     * Exception
     */
    override var exception: Throwable? = null
    /**
     * Counter
     */
    override var counter: UInt = 0u

    /**
     * ResumeWith continuation function
     */



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
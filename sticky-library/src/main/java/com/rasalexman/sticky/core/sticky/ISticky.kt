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

import com.rasalexman.sticky.core.IStickyView
import kotlin.coroutines.Continuation

/**
 *
 */
interface ISticky<V : IStickyView> : Continuation<V> {

    /**
     * Sticky Strategy [StickyStrategy]
     */
    val stickyStrategy: StickyStrategy
        get() = StickyStrategy.Many

    /**
     * Sticky execution block
     */
    var stickyBlock: StickyBlock<V>?

    /**
     * Sticky remove callback
     */
    var removerCallback: StickyRemover<V>?

    /**
     * Sticky Exception
     */
    var exception: Throwable?

    /**
     * Sticky execution counter to use with [StickyStrategy.Counter]
     */
    var counter: UInt

    /**
     * Check sticky state with strategies
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

/**
 *
 */
fun <V : IStickyView> ISticky<V>.clear() {
    removerCallback?.invoke(this)
    removerCallback = null
    exception = null
    stickyBlock = null
}
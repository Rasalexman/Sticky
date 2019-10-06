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

import android.os.Bundle
import androidx.annotation.LayoutRes

/**
 * Interface to take viewModel into [IStickyPresenter]
 */
interface IStickyViewOwner<P : IStickyPresenter<out IStickyView>> : IStickyView {

    /**
     * Flag that say is this a safe fragment without binding of [IStickyView]
     */
    val isSafe: Boolean
        get() = false

    /**
     * [IStickyPresenter] instance
     */
    val presenter: P

    /**
     * Layout Resource Id [LayoutRes]
     */
    val layoutId: Int

    /**
     * on create instance we attach presenter only in does not restore his view
     * Just simple onFirstAttach
     * @param savedInstanceState
     */
    fun create(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            presenter.onFirstAttach(this)
        }
    }

    /**
     * Attach presenter every time when view is ready
     */
    fun attach() {
        presenter.onAttach(this)
    }

    /**
     * Helper function to add view listeners HERE
     */
    fun addListeners() = Unit
}
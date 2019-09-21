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
package com.rasalexman.sticky.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelStoreOwner
import com.rasalexman.sticky.common.StickyException
import com.rasalexman.sticky.core.IStickyPresenter
import com.rasalexman.sticky.core.IStickyView

/**
 * Base Sticky Fragment
 */
abstract class BaseStickyFragment<P : IStickyPresenter<out IStickyView>> : Fragment() {

    /**
     * Is this fragment can be used without [IStickyView]
     */
    open val safeFragment: Boolean = false

    /**
     * [IStickyPresenter] instance
     */
    abstract val presenter: P

    /**
     * Layout Resource Id [LayoutRes]
     */
    abstract val layoutId: Int

    /**
     * on create current fragment
     * @param savedInstanceState [Bundle]
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(this is IStickyView) {
            presenter.attach(this)
        } else if(!safeFragment) {
            throw StickyException.StickyCastException()
        }
    }

    /**
     * Helper function to add view listeners HERE
     */
    open fun addListeners() = Unit

    /**
     * when need to create view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutId, container, false)

    /**
     * when view was created and dded to container
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addListeners()
    }

    /**
     * get [ViewModelStoreOwner]
     */
    open fun getViewModelStoreOwner(): ViewModelStoreOwner {
        return this
    }
}
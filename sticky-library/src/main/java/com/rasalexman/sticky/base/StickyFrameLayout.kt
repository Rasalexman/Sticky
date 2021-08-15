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

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelStoreOwner
import com.rasalexman.sticky.core.IStickyPresenter
import com.rasalexman.sticky.core.IStickyView
import com.rasalexman.sticky.core.IStickyViewOwner


/**
 * Base sticky frame layout
 */
abstract class StickyFrameLayout<P : IStickyPresenter<out IStickyView>> : FrameLayout,
    LifecycleOwner, IStickyViewOwner<P> {

    /**
     * Primary constructor
     * @param context [Context]
     */
    constructor(context: Context) : super(context) {
        initLayout(context)
    }

    /**
     * Secondary constructor
     * @param context [Context]
     * @param attrs [AttributeSet]
     */
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initLayout(context)
    }

    /**
     * Secondary constructor
     * @param context [Context]
     * @param attrs [AttributeSet]
     * @param defStyleAttr [Int]
     */
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initLayout(context)
    }

    /**
     * Secondary constructor for API since LOLLIPOP
     * @param context [Context]
     * @param attrs [AttributeSet]
     * @param defStyleAttr [Int]
     * @param defStyleRes [Int]
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        initLayout(context)
    }

    /**
     * This function is used for init layout
     *
     * @param context app context
     */
    private fun initLayout(context: Context) {
        onViewCreated(LayoutInflater.from(context).inflate(layoutId, this, true))
    }

    /**
     * When finish inflate view attach presenter to the view
     * Note: This is called every time when view is destroyed
     */
    override fun onFinishInflate() {
        super.onFinishInflate()
        create(savedInstanceState = null)
    }

    /**
     * On view attach to window we attach presenter too
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        attach()
    }

    /**
     * on view created and added to this layout
     */
    open fun onViewCreated(view: View) = Unit

    /**
     * get view [Lifecycle] from its [Context]
     */
    override fun getLifecycle(): Lifecycle {
        return getOwner<LifecycleOwner>().lifecycle
    }

    /**
     *  get [ViewModelStoreOwner]
     */
    override fun getViewModelStoreOwner(): ViewModelStoreOwner {
        return getOwner()
    }

    /**
     * Inline function to retrieve [Context] owners
     */
    private inline fun<reified T> getOwner(): T {
        var context: Context = context
        while (context !is T) {
            context = (context as ContextWrapper).baseContext
        }
        return context
    }
}
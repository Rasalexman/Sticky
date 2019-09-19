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

@Suppress("UNCHECKED_CAST")
abstract class BaseStickyFrameLayout<P : IStickyPresenter<out IStickyView>> : FrameLayout, LifecycleOwner {

    /**
     *
     */
    abstract val presenter: P

    /**
     *
     */
    abstract val layoutId: Int

    constructor(context: Context) : super(context) {
        initLayout(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initLayout(context)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initLayout(context)
    }

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
     *
     */
    override fun onFinishInflate() {
        super.onFinishInflate()
        if(this is IStickyView) {
            presenter.attach(this)
        }
    }

    /**
     *
     */
    open fun onViewCreated(view: View) = Unit

    /**
     *
     */
    override fun getLifecycle(): Lifecycle {
        return getOwner<LifecycleOwner>().lifecycle
    }

    /**
     *
     */
    open fun getViewModelStoreOwner(): ViewModelStoreOwner {
        return getOwner()
    }

    /**
     *
     */
    private inline fun<reified T> getOwner(): T {
        var context: Context = context
        while (context !is T) {
            context = (context as ContextWrapper).baseContext
        }
        return context
    }
}
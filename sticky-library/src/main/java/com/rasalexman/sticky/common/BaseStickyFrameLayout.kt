package com.rasalexman.sticky.common

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
import com.rasalexman.sticky.core.IStickyPresenter
import com.rasalexman.sticky.core.IStickyView

@Suppress("UNCHECKED_CAST")
abstract class BaseStickyFrameLayout<V : IStickyView, P : IStickyPresenter<V>> : FrameLayout,
    IStickyView, LifecycleOwner {

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
        presenter.attachView(this as V, lifecycle)
    }

    /**
     *
     */
    open fun onViewCreated(view: View) = Unit

    /**
     *
     */
    override fun getLifecycle(): Lifecycle {
        return getLifecycleOwner().lifecycle
    }

    /**
     *
     */
    protected open fun getLifecycleOwner(): LifecycleOwner {
        var context: Context = context
        while (context !is LifecycleOwner) {
            context = (context as ContextWrapper).baseContext
        }
        return context
    }
}
package com.rasalexman.stickyexample.presentation.start.loading

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.rasalexman.kodi.core.IKodi
import com.rasalexman.kodi.core.immutableInstance
import com.rasalexman.stickyexample.R
import com.rasalexman.sticky.common.YUI
import com.rasalexman.sticky.base.StickyFrameLayout

class LoadingLayout : StickyFrameLayout<ILoadingContract.IPresenter>,
    ILoadingContract.IView, IKodi {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override val layoutId: Int
        get() = R.layout.layout_loading

    override val presenter: ILoadingContract.IPresenter by immutableInstance()

    override fun onViewCreated(view: View) {
        super.onViewCreated(view)
        println("$YUI onViewCreated LoadingLayout")
    }
}
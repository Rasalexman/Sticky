package com.mincor.sticky.presentation.start.loading

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.mincor.kodi.core.IKodi
import com.mincor.kodi.core.immutableInstance
import com.mincor.sticky.R
import com.mincor.sticky.common.YUI
import com.rasalexman.sticky.base.BaseStickyFrameLayout

class LoadingLayout : BaseStickyFrameLayout<ILoadingContract.IPresenter>,
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
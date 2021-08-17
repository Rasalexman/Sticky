package com.rasalexman.stickyexample.presentation.tabs.home.layout

import android.content.Context
import android.util.AttributeSet
import com.rasalexman.kodi.core.IKodi
import com.rasalexman.kodi.core.immutableInstance
import com.rasalexman.sticky.base.StickyFrameLayout
import com.rasalexman.stickyexample.R

class HomeLayout(context: Context, attrs: AttributeSet?) :
    StickyFrameLayout<IHomeLayoutContract.IPresenter>(context, attrs),
    IHomeLayoutContract.IView, IKodi {
    override val layoutId: Int
        get() = R.layout.layout_home
    override val presenter: IHomeLayoutContract.IPresenter by immutableInstance()
}
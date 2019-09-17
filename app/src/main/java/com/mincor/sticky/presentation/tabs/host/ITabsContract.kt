package com.mincor.sticky.presentation.tabs.host

import com.rasalexman.sticky.core.IStickyPresenter
import com.rasalexman.sticky.core.IStickyView

interface ITabsContract {

    interface IView : IStickyView {
        fun showLoading()
        fun hideLoading()
    }

    interface IPresenter : IStickyPresenter<IView>
}
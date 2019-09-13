package com.mincor.sticky.presentation.tabs.home

import com.mincor.sticky.presentation.base.IBaseStickyView
import com.rasalexman.sticky.core.IStickyPresenter

interface IHomeContract {

    interface IView : IBaseStickyView

    interface IPresenter : IStickyPresenter<IView>
}
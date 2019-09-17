package com.mincor.sticky.presentation.tabs.search

import com.mincor.sticky.presentation.base.IBaseStickyView
import com.rasalexman.sticky.core.IStickyPresenter

interface ISearchContract {

    interface IView : IBaseStickyView

    interface IPresenter : IStickyPresenter<IView>
}
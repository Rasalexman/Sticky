package com.rasalexman.stickyexample.presentation.tabs.search

import com.rasalexman.stickyexample.presentation.base.IBaseStickyView
import com.rasalexman.sticky.core.IStickyPresenter

interface ISearchContract {

    interface IView : IBaseStickyView

    interface IPresenter : IStickyPresenter<IView> {
        fun searchByQuery(query: String)
    }
}
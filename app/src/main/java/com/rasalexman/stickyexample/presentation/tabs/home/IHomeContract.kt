package com.rasalexman.stickyexample.presentation.tabs.home

import com.rasalexman.stickyexample.presentation.base.IBaseStickyView
import com.rasalexman.sticky.core.IStickyPresenter

interface IHomeContract {

    interface IView : IBaseStickyView

    interface IPresenter : IStickyPresenter<IView>
}
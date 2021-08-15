package com.rasalexman.stickyexample.presentation.tabs.profile

import com.rasalexman.stickyexample.presentation.base.IBaseStickyView
import com.rasalexman.sticky.core.IStickyPresenter

interface IProfileContract {

    interface IView : IBaseStickyView

    interface IPresenter : IStickyPresenter<IView>
}
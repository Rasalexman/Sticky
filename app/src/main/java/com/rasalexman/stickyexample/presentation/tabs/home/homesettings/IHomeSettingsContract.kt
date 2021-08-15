package com.rasalexman.stickyexample.presentation.tabs.home.homesettings

import com.rasalexman.stickyexample.presentation.base.IBaseStickyView
import com.rasalexman.sticky.core.IStickyPresenter

interface IHomeSettingsContract {

    interface IView : IBaseStickyView

    interface IPresenter : IStickyPresenter<IView>
}
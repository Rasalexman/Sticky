package com.mincor.sticky.presentation.tabs.home.homesettings

import com.mincor.sticky.presentation.base.IBaseStickyView
import com.rasalexman.sticky.core.IStickyPresenter

interface HomeSettingsContract {

    interface IView : IBaseStickyView

    interface IPresenter : IStickyPresenter<IView>
}
package com.mincor.sticky.presentation.tabs.profile

import com.mincor.sticky.presentation.base.IBaseStickyView
import com.rasalexman.sticky.core.IStickyPresenter

interface IProfileContract {

    interface IView : IBaseStickyView

    interface IPresenter : IStickyPresenter<IView>
}
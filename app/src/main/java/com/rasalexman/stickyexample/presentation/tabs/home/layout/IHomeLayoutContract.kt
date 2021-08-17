package com.rasalexman.stickyexample.presentation.tabs.home.layout

import com.rasalexman.sticky.core.IStickyPresenter
import com.rasalexman.sticky.core.IStickyView

interface IHomeLayoutContract {
    interface IView : IStickyView

    interface IPresenter : IStickyPresenter<IView>
}
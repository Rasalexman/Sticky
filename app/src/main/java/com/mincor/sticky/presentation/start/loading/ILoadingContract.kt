package com.mincor.sticky.presentation.start.loading

import com.rasalexman.sticky.core.IStickyPresenter
import com.rasalexman.sticky.core.IStickyView

interface ILoadingContract {

    interface IView : IStickyView

    interface IPresenter : IStickyPresenter<IView>
}
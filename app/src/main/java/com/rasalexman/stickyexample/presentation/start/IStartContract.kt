package com.rasalexman.stickyexample.presentation.start

import com.rasalexman.stickyexample.presentation.base.IBaseStickyView
import com.rasalexman.sticky.core.IStickyPresenter

interface IStartContract  {
    interface IStartView : IBaseStickyView
    interface IStartPresenter : IStickyPresenter<IStartView>
}

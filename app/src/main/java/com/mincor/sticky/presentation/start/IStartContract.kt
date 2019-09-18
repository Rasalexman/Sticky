package com.mincor.sticky.presentation.start

import com.mincor.sticky.presentation.base.IBaseStickyView
import com.rasalexman.sticky.core.IStickyPresenter

interface IStartContract  {
    interface IStartView : IBaseStickyView
    interface IStartPresenter : IStickyPresenter<IStartView>
}

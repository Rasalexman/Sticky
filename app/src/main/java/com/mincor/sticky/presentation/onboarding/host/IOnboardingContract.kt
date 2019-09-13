package com.mincor.sticky.presentation.onboarding.host

import com.mincor.sticky.presentation.base.IBaseStickyView
import com.rasalexman.sticky.core.IStickyPresenter

interface IOnboardingContract {

    interface IView : IBaseStickyView

    interface IPresenter : IStickyPresenter<IView>
}
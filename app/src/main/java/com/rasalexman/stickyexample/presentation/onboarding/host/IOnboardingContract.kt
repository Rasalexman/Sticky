package com.rasalexman.stickyexample.presentation.onboarding.host

import com.rasalexman.stickyexample.presentation.base.IBaseStickyView
import com.rasalexman.sticky.core.IStickyPresenter

interface IOnboardingContract {

    interface IView : IBaseStickyView

    interface IPresenter : IStickyPresenter<IView>
}
package com.rasalexman.stickyexample.presentation.onboarding.signin

import com.rasalexman.stickyexample.presentation.base.IBaseStickyView
import com.rasalexman.sticky.core.IStickyPresenter

interface ISignInContract {

    interface IView : IBaseStickyView

    interface IPresenter : IStickyPresenter<IView> {
        fun onSignInClicked(email: String, password: String)
        fun onRegisterClicked()
    }
}
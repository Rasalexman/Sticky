package com.rasalexman.stickyexample.presentation.onboarding.signup

import com.rasalexman.stickyexample.presentation.base.IBaseStickyView
import com.rasalexman.sticky.core.IStickyPresenter

interface ISignUpContract {

    interface IView : IBaseStickyView

    interface IPresenter : IStickyPresenter<IView> {
        fun onRegisterClicked(
            name: String,
            email: String,
            password: String,
            repeatedPassword: String
        )

        fun onBackClicked()
    }
}
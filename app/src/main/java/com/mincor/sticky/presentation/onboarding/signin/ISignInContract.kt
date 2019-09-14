package com.mincor.sticky.presentation.onboarding.signin

import com.rasalexman.sticky.core.IStickyPresenter
import com.rasalexman.sticky.core.IStickyView

interface ISignInContract {

    interface IView : IStickyView

    interface IPresenter : IStickyPresenter<IView> {
        fun onSignInClicked(email: String, password: String)
        fun onRegisterClicked()
    }
}
package com.rasalexman.stickyexample.presentation.onboarding.signup

import androidx.databinding.ObservableField
import com.rasalexman.sticky.core.IStickyPresenter
import com.rasalexman.stickyexample.presentation.base.IBaseStickyView

interface ISignUpContract {

    interface IView : IBaseStickyView

    interface IPresenter : IStickyPresenter<IView> {
        val email: ObservableField<String>
        val name: ObservableField<String>
        val password: ObservableField<String>
        val repeatedPassword: ObservableField<String>
        val buttonEnabled: ObservableField<Boolean>

        fun onRegisterClicked()
        fun onBackClicked()
    }
}
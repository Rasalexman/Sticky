package com.rasalexman.stickyexample.presentation.onboarding.signin

import android.view.View
import com.rasalexman.kodi.core.IKodi
import com.rasalexman.kodi.core.immutableInstance
import com.rasalexman.sticky.common.UnitHandler
import com.rasalexman.stickyexample.common.YUI
import com.rasalexman.stickyexample.R
import com.rasalexman.stickyexample.databinding.FragmentSignInBinding
import com.rasalexman.stickyexample.navigation.Navigators
import com.rasalexman.stickyexample.presentation.base.BaseViewBindingFragment

class SignInFragment : BaseViewBindingFragment<FragmentSignInBinding, ISignInContract.IPresenter>(),
        ISignInContract.IView, IKodi {

    override val bindHandler: (View) -> FragmentSignInBinding
        get() = FragmentSignInBinding::bind

    override val presenter: ISignInContract.IPresenter by immutableInstance(scope = Navigators.ONBOARDING_NAVIGATOR)

    override val layoutId: Int
        get() = R.layout.fragment_sign_in

    override val loadingLayout: View
        get() = viewBinding.loadingLayout.root

    override val contentLayout: View
        get() = viewBinding.signInLayout.root

    override fun initBinding(viewBinding: FragmentSignInBinding) {
        super.initBinding(viewBinding)
        viewBinding.signInLayout.apply {
            signInButton.setOnClickListener(::signInButtonClickHandler)
            registerButton.setOnClickListener { presenter.onRegisterClicked() }
        }
    }

    private fun signInButtonClickHandler(view: View) {
        viewBinding.signInLayout.apply {
            val email: String = emailEditText.text.toString()
            val password: String = passwordEditText.text.toString()
            presenter.onSignInClicked(email, password)
        }
    }

    override fun showAlertDialog(message: Any, okTitle: Int, okHandler: UnitHandler?) {
        println("$YUI showAlertDialog")
        super.showAlertDialog(message, okTitle, okHandler)
    }
}
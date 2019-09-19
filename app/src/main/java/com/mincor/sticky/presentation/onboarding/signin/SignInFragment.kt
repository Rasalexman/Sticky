package com.mincor.sticky.presentation.onboarding.signin

import android.view.View
import com.mincor.kodi.core.IKodi
import com.mincor.kodi.core.immutableInstance
import com.mincor.sticky.R
import com.mincor.sticky.common.UnitHandler
import com.mincor.sticky.common.YUI
import com.mincor.sticky.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.layout_signin.*

class SignInFragment : BaseFragment<ISignInContract.IPresenter>(),
        ISignInContract.IView, IKodi {

    override val presenter: ISignInContract.IPresenter by immutableInstance()

    override val layoutId: Int
        get() = R.layout.fragment_sign_in

    override fun addListeners() {
        signInButton.setOnClickListener(::signInButtonClickHandler)
        registerButton.setOnClickListener { presenter.onRegisterClicked() }
    }

    private fun signInButtonClickHandler(view: View) {
        val email: String = emailEditText.text.toString()
        val password: String = passwordEditText.text.toString()
        presenter.onSignInClicked(email, password)
    }

    override fun showAlertDialog(message: Any, okTitle: Int, okHandler: UnitHandler?) {
        println("$YUI showAlertDialog")
        super.showAlertDialog(message, okTitle, okHandler)
    }
}
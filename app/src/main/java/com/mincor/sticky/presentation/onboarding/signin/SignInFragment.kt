package com.mincor.sticky.presentation.onboarding.signin

import android.view.View
import android.widget.Toast
import com.mincor.kodi.core.IKodi
import com.mincor.kodi.core.immutableInstance
import com.mincor.sticky.R
import com.mincor.sticky.common.UnitHandler
import com.mincor.sticky.common.YUI
import com.mincor.sticky.common.hide
import com.mincor.sticky.common.show
import com.mincor.sticky.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.layout_signin.*

class SignInFragment : BaseFragment<ISignInContract.IView, ISignInContract.IPresenter>(),
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

    override fun hideLoading() {
        super.hideLoading()
        contentLayout.show()
    }

    override fun showLoading() {
        contentLayout.hide()
        super.showLoading()
    }

    override fun showAlertDialog(message: Int, okTitle: Int, okHandler: UnitHandler?) {
        println("$YUI showAlertDialog")
        super.showAlertDialog(message, okTitle, okHandler)
    }

    override fun showToast(message: String, interval: Int) {
        Toast.makeText(requireContext(), message, interval).show()
    }
}

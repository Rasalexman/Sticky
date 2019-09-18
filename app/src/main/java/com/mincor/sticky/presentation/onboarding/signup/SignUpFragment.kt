package com.mincor.sticky.presentation.onboarding.signup


import android.os.Bundle
import android.view.View
import com.mincor.kodi.core.IKodi
import com.mincor.kodi.core.immutableInstance
import com.mincor.sticky.R
import com.mincor.sticky.common.YUI
import com.mincor.sticky.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : BaseFragment<ISignUpContract.IPresenter>(),
    ISignUpContract.IView, IKodi {

    override val presenter: ISignUpContract.IPresenter by immutableInstance()

    override val layoutId: Int
        get() = R.layout.fragment_sign_up

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("$YUI onViewCreated in SignUpFragment")
    }

    override fun addListeners() {
        registerButton.setOnClickListener(::registerButtonClickHandler)
        backButton.setOnClickListener(::onBackClickHandler)
    }

    private fun registerButtonClickHandler(view: View) {
        val name: String = nameEditText.text.toString()
        val email: String = emailEditText.text.toString()
        val password: String = passwordEditText.text.toString()
        val repeatedPassword: String = passwordRepeateEditText.text.toString()

        presenter.onRegisterClicked(name, email, password, repeatedPassword)
    }

    private fun onBackClickHandler(view: View) {
        presenter.onBackClicked()
    }

}

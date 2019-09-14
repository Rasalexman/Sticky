package com.mincor.sticky.presentation.onboarding.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mincor.sticky.R

class SignInFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }
}/*BaseFragment<ISignInContract.IView, ISignInContract.IPresenter>(),
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
}*/

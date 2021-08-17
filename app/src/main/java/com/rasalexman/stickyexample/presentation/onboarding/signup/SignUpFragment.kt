package com.rasalexman.stickyexample.presentation.onboarding.signup


import android.view.View
import com.rasalexman.kodi.core.IKodi
import com.rasalexman.kodi.core.immutableInstance
import com.rasalexman.stickyexample.R
import com.rasalexman.stickyexample.databinding.FragmentSignUpBinding
import com.rasalexman.stickyexample.navigation.Navigators
import com.rasalexman.stickyexample.presentation.base.BaseDataBindingFragment

class SignUpFragment : BaseDataBindingFragment<FragmentSignUpBinding, ISignUpContract.IPresenter>(),
    ISignUpContract.IView, IKodi {

    override val presenter: ISignUpContract.IPresenter by immutableInstance(scope = Navigators.ONBOARDING_NAVIGATOR)

    override val layoutId: Int
        get() = R.layout.fragment_sign_up

    override val contentLayout: View
        get() = dataBinding.contentLayout

    override val loadingLayout: View
        get() = dataBinding.loadingLayout.root
}

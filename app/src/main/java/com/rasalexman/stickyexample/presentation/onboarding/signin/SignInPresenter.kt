package com.rasalexman.stickyexample.presentation.onboarding.signin

import android.util.Patterns
import com.rasalexman.coroutinesmanager.ICoroutinesManager
import com.rasalexman.coroutinesmanager.launchOnUITryCatch
import com.rasalexman.kodi.core.IKodi
import com.rasalexman.sticky.core.sticky.clear
import com.rasalexman.stickyexample.R
import com.rasalexman.stickyexample.data.local.IUserAccount
import com.rasalexman.stickyexample.data.local.isRegistered
import com.rasalexman.stickyexample.navigation.mainNavigator
import com.rasalexman.stickyexample.navigation.onboardingNavigator

class SignInPresenter(
    private val userAccount: IUserAccount,
    coroutinesManager: ICoroutinesManager
) : ISignInContract.IPresenter, IKodi, ICoroutinesManager by coroutinesManager {

    override fun onSignInClicked(email: String, password: String){
        launchOnUITryCatch(
            tryBlock = {
                view().showLoading()
                view().singleSticky {
                    val okHandler = { it.clear() }

                    when {
                        email.isEmpty() -> showAlertDialog(R.string.error_user_email_empty, okHandler = okHandler)
                        password.isEmpty() -> showAlertDialog(R.string.error_user_password_empty, okHandler = okHandler)
                        !userAccount.isRegistered() -> showAlertDialog(
                            message = R.string.error_user_not_exist,
                            okTitle = R.string.title_go_to_sign_up,
                            okHandler = {
                                it.clear()
                                onRegisterClicked()
                            }
                        )
                        !Patterns.EMAIL_ADDRESS.matcher(email).matches() || (email != userAccount.email) -> {
                            showAlertDialog(R.string.error_user_email_incorrect, okHandler = okHandler)
                        }
                        (password != userAccount.token) -> {
                            showAlertDialog(R.string.error_user_password_incorrect, okHandler = okHandler)
                        }
                        else -> {
                            okHandler()
                            navigateToMainScreen()
                        }
                    }
                }
            }, catchBlock = {
                view().showToast(R.string.error_unexpected)
            }
        )
    }

    override fun onRegisterClicked() {
        onboardingNavigator.navigate(R.id.action_signInFragment_to_signUpFragment)
    }

    private fun navigateToMainScreen() {
        mainNavigator.navigate(R.id.action_onboarding_to_tabsFragment)
    }
}
package com.mincor.sticky.presentation.onboarding.signin

import android.util.Patterns
import androidx.navigation.NavController
import com.mincor.kodi.core.IKodi
import com.mincor.sticky.R
import com.mincor.sticky.common.YUI
import com.mincor.sticky.data.local.IUserAccount
import com.mincor.sticky.data.local.isRegistered
import com.mincor.sticky.navigation.mainNavigator
import com.mincor.sticky.navigation.onboardingNavigator
import com.rasalexman.coroutinesmanager.ICoroutinesManager
import com.rasalexman.coroutinesmanager.launchOnUI
import com.rasalexman.coroutinesmanager.launchOnUITryCatch
import com.rasalexman.sticky.core.BaseStickyPresenter

class SignInPresenter(
    private val userAccount: IUserAccount,
    coroutinesManager: ICoroutinesManager
) : BaseStickyPresenter<ISignInContract.IView>(),
    ISignInContract.IPresenter, IKodi, ICoroutinesManager by coroutinesManager {

    private val navigatorController: NavController by onboardingNavigator()
    private val mainNavigator: NavController by mainNavigator()

    private var valueFromSuspend = 0

    override fun onViewAttached(view: ISignInContract.IView) {
        println("$YUI onViewAttached in SignInPresenter")
    }

    override fun onSignInClicked(email: String, password: String) = launchOnUITryCatch(
        tryBlock = {
            view().showLoading()

            println("$YUI VALUE FROM SUSPEND $valueFromSuspend")

            view().singleSticky {
                when {
                    email.isEmpty() -> showAlertDialog(R.string.error_user_email_empty)
                    password.isEmpty() -> showAlertDialog(R.string.error_user_password_empty)
                    !userAccount.isRegistered() -> showAlertDialog(
                        message = R.string.error_user_not_exist,
                        okTitle = R.string.title_go_to_sign_up,
                        okHandler = ::onRegisterClicked
                    )
                    !Patterns.EMAIL_ADDRESS.matcher(email).matches() || (email != userAccount.email) -> {
                        showAlertDialog(R.string.error_user_email_incorrect)
                    }
                    (password != userAccount.token) -> {
                        showAlertDialog(R.string.error_user_password_incorrect)
                    }
                    else -> {
                        navigateToMainScreen()
                    }
                }
            }
        }, catchBlock = {
            view().showToast(R.string.error_unexpected)
        }
    )

    override fun onRegisterClicked() {
        launchOnUI {
            view().singleSticky {
                navigatorController.navigate(R.id.action_signInFragment_to_signUpFragment)
            }
        }
    }

    private fun navigateToMainScreen() {
        mainNavigator.navigate(R.id.action_onboarding_to_tabsFragment)
    }
}
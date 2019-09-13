package com.mincor.sticky.presentation.onboarding.signup

import android.util.Patterns
import androidx.navigation.NavController
import com.mincor.kodi.core.IKodi
import com.mincor.sticky.R
import com.mincor.sticky.data.local.IUserAccount
import com.mincor.sticky.navigation.mainNavigator
import com.mincor.sticky.navigation.onboardingNavigator
import com.rasalexman.coroutinesmanager.ICoroutinesManager
import com.rasalexman.coroutinesmanager.launchOnUITryCatch
import com.rasalexman.sticky.core.BaseStickyPresenter

class SignUpPresenter(
    private val userAccount: IUserAccount,
    coroutinesManager: ICoroutinesManager
) : BaseStickyPresenter<ISignUpContract.IView>(),
    ISignUpContract.IPresenter, IKodi, ICoroutinesManager by coroutinesManager {

    private val navigatorController: NavController by onboardingNavigator()
    private val mainNavigator: NavController by mainNavigator()

    override fun onRegisterClicked(
        name: String,
        email: String,
        password: String,
        repeatedPassword: String
    ) = launchOnUITryCatch(
        tryBlock = {
            view().stickySuspension<Unit> {
                showLoading()
                when {
                    name.isEmpty() -> showAlertDialog(R.string.error_user_name_empty)
                    email.isEmpty() -> showAlertDialog(R.string.error_user_email_empty)
                    !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                        showAlertDialog(R.string.error_user_email_incorrect)
                    }
                    password.isEmpty() -> showAlertDialog(R.string.error_user_password_empty)
                    (password != repeatedPassword) -> {
                        showAlertDialog(R.string.error_user_repeated_password_incorrect)
                    }
                    else -> {
                        userAccount.name = name
                        userAccount.email = email
                        userAccount.token = password
                        navigateToMainScreen()
                    }
                }
            }
        }, catchBlock = {
            view().showToast(R.string.error_unexpected)
        }
    )

    override fun onBackClicked() {
        navigatorController.popBackStack()
    }

    private fun navigateToMainScreen() {
        mainNavigator.navigate(R.id.action_onboarding_to_tabsFragment)
    }
}

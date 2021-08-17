package com.rasalexman.stickyexample.presentation.onboarding.signup

import android.util.Patterns
import androidx.databinding.ObservableField
import com.rasalexman.coroutinesmanager.ICoroutinesManager
import com.rasalexman.coroutinesmanager.launchOnUITryCatch
import com.rasalexman.kodi.core.IKodi
import com.rasalexman.stickyexample.R
import com.rasalexman.stickyexample.common.combine
import com.rasalexman.stickyexample.data.local.IUserAccount
import com.rasalexman.stickyexample.navigation.mainNavigator
import com.rasalexman.stickyexample.navigation.onboardingNavigator

class SignUpPresenter(
    private val userAccount: IUserAccount,
    coroutinesManager: ICoroutinesManager
) : ISignUpContract.IPresenter, IKodi, ICoroutinesManager by coroutinesManager {


    override val name: ObservableField<String> = ObservableField("")
    override val email: ObservableField<String> = ObservableField("")
    override val password: ObservableField<String> = ObservableField("")
    override val repeatedPassword: ObservableField<String> = ObservableField("")

    override val buttonEnabled: ObservableField<Boolean> = name.combine(
        email, password, repeatedPassword
    ) { data ->
        data.all { it.isNotEmpty() }
    }

    override fun onRegisterClicked() = launchOnUITryCatch(
        tryBlock = {

            val name: String = name.get().orEmpty()
            val email: String = email.get().orEmpty()
            val password: String = password.get().orEmpty()
            val repeatedPassword: String = repeatedPassword.get().orEmpty()

            view().singleSticky {
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
            println("-----> onRegisterClicked error $it")
            view().showToast(R.string.error_unexpected)
        }
    )

    override fun onBackClicked() {
        onboardingNavigator.popBackStack()
    }

    private fun navigateToMainScreen() {
        mainNavigator.navigate(R.id.action_onboarding_to_tabsFragment)
    }
}

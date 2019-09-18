package com.mincor.sticky.presentation.onboarding.host


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.mincor.kodi.core.*
import com.mincor.sticky.R
import com.mincor.sticky.navigation.Navigators.ONBOARDING_NAVIGATOR
import com.mincor.sticky.presentation.base.BaseHostFragment
import com.mincor.sticky.presentation.onboarding.signin.ISignInContract
import com.mincor.sticky.presentation.onboarding.signin.SignInPresenter
import com.mincor.sticky.presentation.onboarding.signup.ISignUpContract
import com.mincor.sticky.presentation.onboarding.signup.SignUpPresenter

/**
 * A simple [Fragment] subclass.
 */
class OnboardingFragment : BaseHostFragment<IOnboardingContract.IPresenter>(),
    IOnboardingContract.IView, IKodi {

    override val presenter: IOnboardingContract.IPresenter
            get() = instance()

    override val layoutId: Int
        get() = R.layout.host_fragment_onboarding

    override val navControllerId: Int
        get() = R.id.onboardingHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        if(savedInstanceState == null) {
            import(onBoardingModule)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navHostController?.let { onBoardingNavController ->
            unbind<NavController>(ONBOARDING_NAVIGATOR)
            bind<NavController>(ONBOARDING_NAVIGATOR) with provider { onBoardingNavController }
        }
    }

    companion object {
        private val onBoardingModule = kodiModule {
            bind<IOnboardingContract.IPresenter>()      with single {
                OnboardingPresenter(
                    instance(),
                    instance()
                )
            }
            bind<ISignInContract.IPresenter>()          with single { SignInPresenter(instance(), instance()) }
            bind<ISignUpContract.IPresenter>()          with single { SignUpPresenter(instance(), instance()) }
        } withScope ONBOARDING_NAVIGATOR.asScope()
    }
}



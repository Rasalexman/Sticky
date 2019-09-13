package com.mincor.sticky.presentation.onboarding.host


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mincor.kodi.core.*
import com.mincor.sticky.R
import com.mincor.sticky.navigation.Navigators.ONBOARDING_NAVIGATOR
import com.mincor.sticky.presentation.base.BaseFragment
import com.mincor.sticky.presentation.onboarding.signin.ISignInContract
import com.mincor.sticky.presentation.onboarding.signin.SignInPresenter
import com.mincor.sticky.presentation.onboarding.signup.ISignUpContract
import com.mincor.sticky.presentation.onboarding.signup.SignUpPresenter

/**
 * A simple [Fragment] subclass.
 */
class OnboardingFragment : BaseFragment<IOnboardingContract.IView, IOnboardingContract.IPresenter>(),
    IOnboardingContract.IView, IKodi {

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

    init {
        kodi {
            import(onBoardingModule)
        }
    }

    override val presenter: IOnboardingContract.IPresenter by immutableInstance()
    override val layoutId: Int
        get() = R.layout.host_fragment_onboarding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.activity?.let { liveActivity ->
            unbind<NavController>(ONBOARDING_NAVIGATOR)
            bind<NavController>(ONBOARDING_NAVIGATOR) with provider {
                Navigation.findNavController(liveActivity, R.id.onboardingHostFragment)
            }
        }
    }
}



package com.rasalexman.stickyexample.presentation.onboarding.host

import com.rasalexman.sticky.common.YUI
import com.rasalexman.coroutinesmanager.ICoroutinesManager
import com.rasalexman.coroutinesmanager.launchOnUITryCatch
import com.rasalexman.stickyexample.presentation.onboarding.host.IOnboardingContract

class OnboardingPresenter(
    coroutinesManager: ICoroutinesManager
) : IOnboardingContract.IPresenter, ICoroutinesManager by coroutinesManager {

    override fun onViewCreated(view: IOnboardingContract.IView) = launchOnUITryCatch(
        tryBlock = {
            println("$YUI HELLO THIS IS A OnboardingPresenter")
        }, catchBlock = {
            println("$YUI SOME THING GOES WRONG")
        }
    )
}
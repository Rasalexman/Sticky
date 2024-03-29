package com.rasalexman.stickyexample.presentation.onboarding.host

import com.rasalexman.stickyexample.common.YUI
import com.rasalexman.coroutinesmanager.ICoroutinesManager
import com.rasalexman.coroutinesmanager.launchOnUITryCatch

class OnboardingPresenter(
    coroutinesManager: ICoroutinesManager
) : IOnboardingContract.IPresenter, ICoroutinesManager by coroutinesManager {

    override fun onViewCreated(view: IOnboardingContract.IView) {
        launchOnUITryCatch(
            tryBlock = {
                println("$YUI HELLO THIS IS A OnboardingPresenter")
            }, catchBlock = {
                println("$YUI SOME THING GOES WRONG")
            }
        )
    }
}
package com.mincor.sticky.presentation.onboarding.host

import com.mincor.sticky.common.YUI
import com.mincor.sticky.data.local.IUserAccount
import com.rasalexman.coroutinesmanager.ICoroutinesManager
import com.rasalexman.coroutinesmanager.launchOnUITryCatch
import com.rasalexman.sticky.core.BaseStickyPresenter

class OnboardingPresenter(
    private val userAccount: IUserAccount,
    coroutinesManager: ICoroutinesManager
) : BaseStickyPresenter<IOnboardingContract.IView>(),
    IOnboardingContract.IPresenter, ICoroutinesManager by coroutinesManager {

    override fun onViewAttached(view: IOnboardingContract.IView) = launchOnUITryCatch(
        tryBlock = {
            println("$YUI HELLO THIS IS A OnboardingPresenter")
        }, catchBlock = {
            println("$YUI SOME THING GOES WRONG")
        }
    )
}
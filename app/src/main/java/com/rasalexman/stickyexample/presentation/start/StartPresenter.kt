package com.rasalexman.stickyexample.presentation.start

import com.rasalexman.coroutinesmanager.ICoroutinesManager
import com.rasalexman.coroutinesmanager.launchOnUITryCatch
import com.rasalexman.kodi.core.IKodi
import com.rasalexman.stickyexample.common.YUI
import com.rasalexman.stickyexample.R
import com.rasalexman.stickyexample.data.local.IUserAccount
import com.rasalexman.stickyexample.data.local.isRegistered
import com.rasalexman.stickyexample.navigation.mainNavigator

data class StartPresenter(
    private val userAccount: IUserAccount
) : BaseStartPresenter(), IKodi, ICoroutinesManager {

    override fun onViewCreated(view: IStartContract.IStartView) = launchOnUITryCatch(
        tryBlock = {
            println("$YUI HELLO THIS IS A START PRESENTER with navigator $mainNavigator")

            view().singleSticky {
                showLoading()
                if(userAccount.isRegistered()) {
                    mainNavigator.navigate(R.id.action_startFragment_to_tabFragment)
                } else {
                    mainNavigator.navigate(R.id.action_startFragment_to_onboarding)
                }
            }
        }, catchBlock = {
            view().showToast(R.string.error_unexpected)
        }
    )

    override fun onViewDestroyed(view: IStartContract.IStartView) {
        cleanup()
    }
}

abstract class BaseStartPresenter : IStartContract.IStartPresenter
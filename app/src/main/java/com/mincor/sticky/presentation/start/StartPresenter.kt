package com.mincor.sticky.presentation.start

import androidx.navigation.NavController
import com.mincor.kodi.core.IKodi
import com.mincor.sticky.R
import com.mincor.sticky.common.YUI
import com.mincor.sticky.data.local.IUserAccount
import com.mincor.sticky.data.local.isRegistered
import com.mincor.sticky.navigation.mainNavigator
import com.rasalexman.coroutinesmanager.ICoroutinesManager
import com.rasalexman.coroutinesmanager.launchOnUITryCatch

data class StartPresenter(
    private val userAccount: IUserAccount
) : BaseStartPresenter(), IKodi, ICoroutinesManager {

    private val mainNavigator: NavController by mainNavigator()

    override fun onViewAttached(view: IStartContract.IStartView) = launchOnUITryCatch(
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

    override fun onViewDettached(view: IStartContract.IStartView) {
        cleanup()
    }
}

abstract class BaseStartPresenter : IStartContract.IStartPresenter
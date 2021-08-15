package com.rasalexman.stickyexample.presentation.tabs.profile

import androidx.navigation.NavController
import com.rasalexman.kodi.core.IKodi
import com.rasalexman.stickyexample.R
import com.rasalexman.sticky.common.YUI
import com.rasalexman.stickyexample.navigation.tabNavigator
import com.rasalexman.coroutinesmanager.ICoroutinesManager
import com.rasalexman.coroutinesmanager.launchOnUITryCatch

class ProfilePresenter(
    coroutinesManager: ICoroutinesManager
) : IProfileContract.IPresenter, IKodi, ICoroutinesManager by coroutinesManager {

    private val tabNavigator: NavController by tabNavigator()

    override fun onViewCreated(view: IProfileContract.IView) = launchOnUITryCatch(
        tryBlock = {
            println("$YUI HELLO THIS IS A ProfilePresenter")
            view().showLoading()
        }, catchBlock = {
            view().showToast(R.string.error_unexpected)
        }
    )
}
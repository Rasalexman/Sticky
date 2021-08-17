package com.rasalexman.stickyexample.presentation.tabs.profile

import com.rasalexman.coroutinesmanager.ICoroutinesManager
import com.rasalexman.coroutinesmanager.launchOnUITryCatch
import com.rasalexman.kodi.core.IKodi
import com.rasalexman.stickyexample.common.YUI
import com.rasalexman.stickyexample.R

class ProfilePresenter(
    coroutinesManager: ICoroutinesManager
) : IProfileContract.IPresenter, IKodi, ICoroutinesManager by coroutinesManager {

    override fun onViewCreated(view: IProfileContract.IView) = launchOnUITryCatch(
        tryBlock = {
            println("$YUI HELLO THIS IS A ProfilePresenter")
            view().showLoading()
        }, catchBlock = {
            view().showToast(R.string.error_unexpected)
        }
    )
}
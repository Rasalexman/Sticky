package com.mincor.sticky.presentation.tabs.home

import androidx.navigation.NavController
import com.mincor.kodi.core.IKodi
import com.mincor.sticky.R
import com.mincor.sticky.common.YUI
import com.mincor.sticky.navigation.tabNavigator
import com.rasalexman.coroutinesmanager.ICoroutinesManager
import com.rasalexman.coroutinesmanager.launchOnUITryCatch
import com.rasalexman.sticky.core.BaseStickyPresenter

class HomePresenter(
    coroutinesManager: ICoroutinesManager
) : BaseStickyPresenter<IHomeContract.IView>(),
    IHomeContract.IPresenter, IKodi, ICoroutinesManager by coroutinesManager {

    private val tabNavigator: NavController by tabNavigator()

    override fun onViewAttached(view: IHomeContract.IView) = launchOnUITryCatch(
        tryBlock = {
            println("$YUI HELLO THIS IS A HomePresenter")
            view().showLoading()
        }, catchBlock = {
            view().showToast(R.string.error_unexpected)
        }
    )
}
package com.mincor.sticky.presentation.tabs.home

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.mincor.kodi.core.IKodi
import com.mincor.sticky.R
import com.mincor.sticky.navigation.tabNavigator
import com.rasalexman.coroutinesmanager.ICoroutinesManager
import com.rasalexman.coroutinesmanager.launchOnUITryCatch
import com.rasalexman.sticky.core.BaseStickyPresenter

class HomePresenter(
    coroutinesManager: ICoroutinesManager
) : BaseStickyPresenter<IHomeContract.IView>(),
    IHomeContract.IPresenter, IKodi, ICoroutinesManager by coroutinesManager {

    override val viewAvailableState: Lifecycle.State = Lifecycle.State.CREATED

    private val tabNavigator: NavController by tabNavigator()

    override fun onViewAttached(view: IHomeContract.IView) = launchOnUITryCatch(
        tryBlock = {
            println("------> HELLO THIS IS A START PRESENTER")
            view().showLoading()
        }, catchBlock = {
            view().showToast(R.string.error_unexpected)
        }
    )
}
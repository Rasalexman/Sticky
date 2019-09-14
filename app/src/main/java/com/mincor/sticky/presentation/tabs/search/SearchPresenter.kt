package com.mincor.sticky.presentation.tabs.search

import androidx.navigation.NavController
import com.mincor.kodi.core.IKodi
import com.mincor.sticky.R
import com.mincor.sticky.common.YUI
import com.mincor.sticky.navigation.tabNavigator
import com.rasalexman.coroutinesmanager.ICoroutinesManager
import com.rasalexman.coroutinesmanager.launchOnUITryCatch
import com.rasalexman.sticky.core.BaseStickyPresenter

class SearchPresenter(
    coroutinesManager: ICoroutinesManager
) : BaseStickyPresenter<ISearchContract.IView>(),
    ISearchContract.IPresenter, IKodi, ICoroutinesManager by coroutinesManager {

    private val tabNavigator: NavController by tabNavigator()

    override fun onViewAttached(view: ISearchContract.IView) = launchOnUITryCatch(
        tryBlock = {
            println("$YUI HELLO THIS IS A SearchPresenter")
            view().showLoading()
        }, catchBlock = {
            println("$YUI catchBlock in SearchPresenter")
            view().showToast(R.string.error_unexpected)
        }
    )
}
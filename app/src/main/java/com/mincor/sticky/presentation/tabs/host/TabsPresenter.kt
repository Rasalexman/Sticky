package com.mincor.sticky.presentation.tabs.host

import com.mincor.sticky.common.YUI
import com.rasalexman.coroutinesmanager.ICoroutinesManager
import com.rasalexman.coroutinesmanager.launchOnUITryCatch

class TabsPresenter(
    coroutinesManager: ICoroutinesManager
) : ITabsContract.IPresenter, ICoroutinesManager by coroutinesManager {

    override fun onViewAttached(view: ITabsContract.IView) = launchOnUITryCatch(
        tryBlock = {
            println("$YUI HELLO THIS IS A TabsPresenter")

        }, catchBlock = {

        }
    )
}
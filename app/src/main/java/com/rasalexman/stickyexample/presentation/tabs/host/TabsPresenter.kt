package com.rasalexman.stickyexample.presentation.tabs.host

import com.rasalexman.stickyexample.common.YUI
import com.rasalexman.coroutinesmanager.ICoroutinesManager
import com.rasalexman.coroutinesmanager.launchOnUITryCatch

class TabsPresenter(
    coroutinesManager: ICoroutinesManager
) : ITabsContract.IPresenter, ICoroutinesManager by coroutinesManager {

    override fun onViewCreated(view: ITabsContract.IView) = launchOnUITryCatch(
        tryBlock = {
            println("$YUI HELLO THIS IS A TabsPresenter")

        }, catchBlock = {

        }
    )
}
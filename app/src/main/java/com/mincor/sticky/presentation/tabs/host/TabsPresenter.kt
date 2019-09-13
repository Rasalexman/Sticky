package com.mincor.sticky.presentation.tabs.host

import androidx.lifecycle.Lifecycle
import com.rasalexman.coroutinesmanager.ICoroutinesManager
import com.rasalexman.coroutinesmanager.launchOnUITryCatch
import com.rasalexman.sticky.core.BaseStickyPresenter

class TabsPresenter(
    coroutinesManager: ICoroutinesManager
) : BaseStickyPresenter<ITabsContract.IView>(),
    ITabsContract.IPresenter, ICoroutinesManager by coroutinesManager {

    override val viewAvailableState: Lifecycle.State = Lifecycle.State.CREATED

    override fun onViewAttached(view: ITabsContract.IView) = launchOnUITryCatch(
        tryBlock = {
            println("------> HELLO THIS IS A TabsPresenter")

        }, catchBlock = {

        }
    )
}
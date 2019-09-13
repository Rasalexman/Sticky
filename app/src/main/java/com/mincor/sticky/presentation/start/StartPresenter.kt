package com.mincor.sticky.presentation.start

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.mincor.kodi.core.IKodi
import com.mincor.sticky.R
import com.mincor.sticky.data.local.IUserAccount
import com.mincor.sticky.data.local.isRegistered
import com.mincor.sticky.navigation.mainNavigator
import com.rasalexman.coroutinesmanager.ICoroutinesManager
import com.rasalexman.coroutinesmanager.launchOnUITryCatch
import com.rasalexman.sticky.core.BaseStickyPresenter

class StartPresenter(
    private val userAccount: IUserAccount,
    coroutinesManager: ICoroutinesManager
) : BaseStickyPresenter<IStartContract.IView>(),
    IStartContract.IPresenter, IKodi, ICoroutinesManager by coroutinesManager {

    override val viewAvailableState: Lifecycle.State = Lifecycle.State.CREATED

    private val mainNavigator: NavController by mainNavigator()

    override fun onViewAttached(view: IStartContract.IView) = launchOnUITryCatch(
        tryBlock = {
            println("------> HELLO THIS IS A START PRESENTER")

            view().showLoading()

            if(userAccount.isRegistered()) {
                mainNavigator.navigate(R.id.action_startFragment_to_tabFragment)
            } else {
                mainNavigator.navigate(R.id.action_startFragment_to_onboarding)
            }
        }, catchBlock = {
            view().showToast(R.string.error_unexpected)
        }
    )
}
package com.mincor.sticky.application

import android.app.Application
import android.content.Context
import com.chibatching.kotpref.Kotpref
import com.mincor.kodi.core.*
import com.mincor.sticky.data.local.IUserAccount
import com.mincor.sticky.data.local.UserAccount
import com.mincor.sticky.presentation.start.loading.ILoadingContract
import com.mincor.sticky.presentation.start.loading.LoadingPresenter
import com.rasalexman.coroutinesmanager.CoroutinesManager
import com.rasalexman.coroutinesmanager.ICoroutinesManager

class MainApplication : Application() {

    val kodi = kodi {
        bind<Context>()                     with provider { applicationContext }

        bind<ICoroutinesManager>()          with single { CoroutinesManager() }

        bind<IUserAccount>()                with single { UserAccount(instance()) }

        bind<ILoadingContract.IPresenter>() with single { LoadingPresenter() }
    }


    override fun onCreate() {
        super.onCreate()
        Kotpref.init(applicationContext)
    }

}
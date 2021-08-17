package com.rasalexman.stickyexample.application

import android.app.Application
import android.content.Context
import com.chibatching.kotpref.Kotpref
import com.rasalexman.kodi.core.*
import com.rasalexman.stickyexample.data.local.IUserAccount
import com.rasalexman.stickyexample.data.local.UserAccount
import com.rasalexman.stickyexample.presentation.start.loading.ILoadingContract
import com.rasalexman.stickyexample.presentation.start.loading.LoadingPresenter
import com.rasalexman.coroutinesmanager.CoroutinesManager
import com.rasalexman.coroutinesmanager.ICoroutinesManager
import com.rasalexman.stickyexample.presentation.tabs.home.HomePresenter
import com.rasalexman.stickyexample.presentation.tabs.home.IHomeContract
import com.rasalexman.stickyexample.presentation.tabs.home.layout.HomeLayoutPresenter
import com.rasalexman.stickyexample.presentation.tabs.home.layout.IHomeLayoutContract

class MainApplication : Application() {

    val kodi = kodi {
        bind<Context>()                     with provider { applicationContext }

        bind<ICoroutinesManager>()          with single { CoroutinesManager() }

        bind<IUserAccount>()                with single { UserAccount(instance()) }

        bind<ILoadingContract.IPresenter>() with single { LoadingPresenter() }

        bind<IHomeContract.IPresenter>()  with single { HomePresenter(instance()) }
        
        bind<IHomeLayoutContract.IPresenter>()  with single { HomeLayoutPresenter() }

    }


    override fun onCreate() {
        super.onCreate()
        Kotpref.init(applicationContext)
    }

}
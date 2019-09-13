package com.mincor.sticky.application

import android.app.Application
import android.content.Context
import com.chibatching.kotpref.Kotpref
import com.mincor.kodi.core.*
import com.mincor.sticky.data.local.IUserAccount
import com.mincor.sticky.data.local.UserAccount
import com.rasalexman.coroutinesmanager.CoroutinesManager
import com.rasalexman.coroutinesmanager.ICoroutinesManager

class MainApplication : Application() {

    val kodi = kodi {
        bind<Context>()                     with provider { applicationContext }

        bind<ICoroutinesManager>()          with single { CoroutinesManager() }

        bind<IUserAccount>()                with single { UserAccount(instance()) }
    }


    override fun onCreate() {
        super.onCreate()
        Kotpref.init(applicationContext)
    }

}
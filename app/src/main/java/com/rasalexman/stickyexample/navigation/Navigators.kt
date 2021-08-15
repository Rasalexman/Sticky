package com.rasalexman.stickyexample.navigation

import androidx.navigation.NavController
import com.rasalexman.kodi.core.IKodi
import com.rasalexman.kodi.core.immutableInstance
import com.rasalexman.kodi.delegates.IImmutableDelegate
import com.rasalexman.stickyexample.navigation.Navigators.MAIN_NAVIGATOR
import com.rasalexman.stickyexample.navigation.Navigators.ONBOARDING_NAVIGATOR
import com.rasalexman.stickyexample.navigation.Navigators.TAB_NAVIGATOR

object Navigators {
    const val MAIN_NAVIGATOR: String = "Main"
    const val ONBOARDING_NAVIGATOR: String = "Onboarding"
    const val TAB_NAVIGATOR: String = "Tab"
}

fun IKodi.mainNavigator(): IImmutableDelegate<NavController> = immutableInstance(scope = MAIN_NAVIGATOR)
fun IKodi.onboardingNavigator(): IImmutableDelegate<NavController> = immutableInstance(scope = ONBOARDING_NAVIGATOR)
fun IKodi.tabNavigator(): IImmutableDelegate<NavController> = immutableInstance(scope = TAB_NAVIGATOR)
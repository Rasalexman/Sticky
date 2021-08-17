package com.rasalexman.stickyexample.navigation

import androidx.navigation.NavController
import com.rasalexman.kodi.core.IKodi
import com.rasalexman.kodi.core.immutableInstance
import com.rasalexman.kodi.core.instance
import com.rasalexman.kodi.delegates.IImmutableDelegate
import com.rasalexman.stickyexample.navigation.Navigators.MAIN_NAVIGATOR
import com.rasalexman.stickyexample.navigation.Navigators.ONBOARDING_NAVIGATOR
import com.rasalexman.stickyexample.navigation.Navigators.TAB_NAVIGATOR

object Navigators {
    const val MAIN_NAVIGATOR: String = "Main"
    const val ONBOARDING_NAVIGATOR: String = "Onboarding"
    const val TAB_NAVIGATOR: String = "Tab"
}

val IKodi.mainNavigator: NavController get() = instance(scope = MAIN_NAVIGATOR)
val IKodi.onboardingNavigator: NavController get() = instance(scope = ONBOARDING_NAVIGATOR)
val IKodi.tabNavigator: NavController get() = instance(scope = TAB_NAVIGATOR)
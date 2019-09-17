package com.mincor.sticky.navigation

import androidx.navigation.NavController
import com.mincor.kodi.core.IKodi
import com.mincor.kodi.core.immutableInstance
import com.mincor.kodi.delegates.IImmutableDelegate
import com.mincor.sticky.navigation.Navigators.MAIN_NAVIGATOR
import com.mincor.sticky.navigation.Navigators.ONBOARDING_NAVIGATOR
import com.mincor.sticky.navigation.Navigators.TAB_NAVIGATOR

object Navigators {
    const val MAIN_NAVIGATOR: String = "Main"
    const val ONBOARDING_NAVIGATOR: String = "Onboarding"
    const val TAB_NAVIGATOR: String = "Tab"
}

fun IKodi.mainNavigator(): IImmutableDelegate<NavController> = immutableInstance(MAIN_NAVIGATOR)
fun IKodi.onboardingNavigator(): IImmutableDelegate<NavController> = immutableInstance(ONBOARDING_NAVIGATOR)
fun IKodi.tabNavigator(): IImmutableDelegate<NavController> = immutableInstance(TAB_NAVIGATOR)
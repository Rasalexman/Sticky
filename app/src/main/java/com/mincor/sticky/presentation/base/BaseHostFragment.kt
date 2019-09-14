package com.mincor.sticky.presentation.base

import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.rasalexman.sticky.core.IStickyPresenter
import com.rasalexman.sticky.core.IStickyView

abstract class BaseHostFragment<V : IStickyView, P : IStickyPresenter<V>> : BaseFragment<V, P>()  {

    open val navControllerId: Int = -1

    open val navHostController: NavController?
        get() = this.activity?.let { liveActivity ->
            Navigation.findNavController(liveActivity, navControllerId)
        }

    override fun onBackPressed(): Boolean {
        return true
    }
}
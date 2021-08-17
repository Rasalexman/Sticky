package com.rasalexman.stickyexample.presentation.tabs.home


import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.rasalexman.kodi.core.IKodi
import com.rasalexman.kodi.core.immutableInstance
import com.rasalexman.kodi.core.instance
import com.rasalexman.stickyexample.R
import com.rasalexman.stickyexample.databinding.FragmentHomeBinding
import com.rasalexman.stickyexample.presentation.base.BaseViewBindingFragment


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseViewBindingFragment<FragmentHomeBinding, IHomeContract.IPresenter>(),
    IHomeContract.IView, IKodi {

    override val presenter: IHomeContract.IPresenter by immutableInstance()

    override val layoutId: Int
        get() = R.layout.fragment_home

    override val bindHandler: (View) -> FragmentHomeBinding
        get() = FragmentHomeBinding::bind

    override val toolbar: Toolbar
        get() = viewBinding.toolbarView

    override fun onMenuItemClick(item: MenuItem): Boolean {
        findNavController().navigateWithMenuAnimation(
            item,
            R.anim.slide_in_right,
            R.anim.slide_out_left,
            R.anim.slide_in_left,
            R.anim.slide_out_right
        )
        return super.onMenuItemClick(item)
    }

    override fun onDestroyView() {
        presenter.onViewDestroyed(this)
        super.onDestroyView()
    }
}

fun NavController.navigateWithMenuAnimation(
    item: MenuItem,
    enterAnim: Int = R.anim.nav_default_enter_anim,
    exitAnim: Int = R.anim.nav_default_exit_anim,
    popEnterAnim: Int = R.anim.nav_default_pop_enter_anim,
    popExitAnim: Int = R.anim.nav_default_pop_exit_anim,
    args: Bundle? = null
): Boolean {
    val builder = NavOptions.Builder()
        .setLaunchSingleTop(true)
        .setEnterAnim(enterAnim)
        .setExitAnim(exitAnim)
        .setPopEnterAnim(popEnterAnim)
        .setPopExitAnim(popExitAnim)
    if (item.order and Menu.CATEGORY_SECONDARY == 0) {
        builder.setPopUpTo(this.graph.findStartDestination().id, false)
    }

    return try {
        this.navigate(item.itemId, args, builder.build())
        true
    } catch (e: IllegalArgumentException) {
        false
    }

}

fun NavGraph.findStartDestination(): NavDestination {
    var startDestination: NavDestination? = this
    while (startDestination is NavGraph) {
        val parent = startDestination
        startDestination = parent.findNode(parent.startDestination)
    }
    return startDestination ?: this
}

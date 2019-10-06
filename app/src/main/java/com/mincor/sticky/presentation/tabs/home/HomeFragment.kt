package com.mincor.sticky.presentation.tabs.home


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
import com.mincor.kodi.core.IKodi
import com.mincor.kodi.core.instance
import com.mincor.sticky.R
import com.mincor.sticky.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment<IHomeContract.IPresenter>(),
    IHomeContract.IView, IKodi {

    override val presenter: IHomeContract.IPresenter
        get() = HomePresenter(instance())

    override val layoutId: Int
        get() = R.layout.fragment_home

    override val toolbar: Toolbar?
        get() = toolbarView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_navigation_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().navigateWithMenuAnimation(item, R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
        return super.onOptionsItemSelected(item)
    }
}

fun NavController.navigateWithMenuAnimation(
    item: MenuItem,
    enterAnim: Int = R.anim.nav_default_enter_anim,
    exitAnim: Int = R.anim.nav_default_exit_anim,
    popEnterAnim: Int = R.anim.nav_default_pop_enter_anim,
    popExitAnim: Int = R.anim.nav_default_pop_exit_anim,
    args: Bundle? = null): Boolean {
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

package com.mincor.sticky.presentation.tabs.host


import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import com.mincor.kodi.core.*
import com.mincor.sticky.R
import com.mincor.sticky.navigation.Navigators.TAB_NAVIGATOR
import com.mincor.sticky.presentation.base.BaseHostFragment
import com.mincor.sticky.presentation.tabs.home.HomePresenter
import com.mincor.sticky.presentation.tabs.home.IHomeContract
import com.mincor.sticky.presentation.tabs.profile.IProfileContract
import com.mincor.sticky.presentation.tabs.profile.ProfilePresenter
import com.mincor.sticky.presentation.tabs.search.ISearchContract
import com.mincor.sticky.presentation.tabs.search.SearchPresenter
import kotlinx.android.synthetic.main.host_fragment_tabs.*

/**
 * A simple [BaseHostFragment] subclass.
 */
class TabsFragment : BaseHostFragment<ITabsContract.IPresenter>(),
    ITabsContract.IView, IKodi {

    override val presenter: ITabsContract.IPresenter by immutableInstance()
    override val layoutId: Int
        get() = R.layout.host_fragment_tabs

    override val navControllerId: Int
        get() = R.id.tabsHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        if(savedInstanceState == null) {
            import(tabsModule)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navHostController?.let { tabNavController ->
            NavigationUI.setupWithNavController(bottomNavigationView, tabNavController)
            unbind<NavController>(TAB_NAVIGATOR)
            bind<NavController>(TAB_NAVIGATOR) with provider { tabNavController }
        }
    }

    companion object {
        private val tabsModule = kodiModule {
            bind<ITabsContract.IPresenter>()        with single { TabsPresenter(instance()) }

            bind<IHomeContract.IPresenter>()        with single { HomePresenter(instance()) }
            bind<IProfileContract.IPresenter>()     with single { ProfilePresenter(instance()) }
            bind<ISearchContract.IPresenter>()      with single { SearchPresenter(instance()) }
        } withScope TAB_NAVIGATOR.asScope()
    }
}

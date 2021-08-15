package com.rasalexman.stickyexample.presentation.tabs.host


import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import com.rasalexman.kodi.core.*
import com.rasalexman.stickyexample.R
import com.rasalexman.stickyexample.navigation.Navigators.TAB_NAVIGATOR
import com.rasalexman.stickyexample.presentation.base.BaseHostFragment
import com.rasalexman.stickyexample.presentation.tabs.home.HomePresenter
import com.rasalexman.stickyexample.presentation.tabs.home.IHomeContract
import com.rasalexman.stickyexample.presentation.tabs.home.homesettings.IHomeSettingsContract
import com.rasalexman.stickyexample.presentation.tabs.home.homesettings.HomeSettingsPresenter
import com.rasalexman.stickyexample.presentation.tabs.profile.IProfileContract
import com.rasalexman.stickyexample.presentation.tabs.profile.ProfilePresenter
import com.rasalexman.stickyexample.presentation.tabs.search.ISearchContract
import com.rasalexman.stickyexample.presentation.tabs.search.SearchPresenter
import com.rasalexman.stickyexample.presentation.tabs.search.SearchViewModel
import kotlinx.android.synthetic.main.host_fragment_tabs.*

/**
 * A simple [BaseHostFragment] subclass.
 */
class TabsFragment : BaseHostFragment<ITabsContract.IPresenter>(),
    ITabsContract.IView, IKodi {

    override val presenter: ITabsContract.IPresenter by immutableInstance(scope = TAB_NAVIGATOR)
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
            unbind<NavController>(scope = TAB_NAVIGATOR)
            bind<NavController>() at TAB_NAVIGATOR with provider { tabNavController }
        }
    }

    companion object {
        private val tabsModule = kodiModule {
            bind<ITabsContract.IPresenter>()        with single { TabsPresenter(instance()) }

            bind<SearchViewModel>()                 with single { SearchViewModel() }

            bind<IHomeContract.IPresenter>()        with single { HomePresenter(instance()) }
            bind<IProfileContract.IPresenter>()     with single { ProfilePresenter(instance()) }
            bind<ISearchContract.IPresenter>()      with single { SearchPresenter(instance(scope = TAB_NAVIGATOR), instance()) }

            bind<IHomeSettingsContract.IPresenter>() with provider { HomeSettingsPresenter() }
        } withScope TAB_NAVIGATOR
    }
}

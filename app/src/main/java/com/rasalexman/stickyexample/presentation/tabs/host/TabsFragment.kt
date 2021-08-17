package com.rasalexman.stickyexample.presentation.tabs.host


import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.rasalexman.kodi.core.*
import com.rasalexman.stickyexample.R
import com.rasalexman.stickyexample.databinding.HostFragmentTabsBinding
import com.rasalexman.stickyexample.navigation.Navigators.TAB_NAVIGATOR
import com.rasalexman.stickyexample.presentation.base.BaseHostFragment
import com.rasalexman.stickyexample.presentation.base.BaseViewBindingFragment
import com.rasalexman.stickyexample.presentation.tabs.home.HomePresenter
import com.rasalexman.stickyexample.presentation.tabs.home.IHomeContract
import com.rasalexman.stickyexample.presentation.tabs.home.homesettings.IHomeSettingsContract
import com.rasalexman.stickyexample.presentation.tabs.home.homesettings.HomeSettingsPresenter
import com.rasalexman.stickyexample.presentation.tabs.home.layout.IHomeLayoutContract
import com.rasalexman.stickyexample.presentation.tabs.home.layout.HomeLayoutPresenter
import com.rasalexman.stickyexample.presentation.tabs.profile.IProfileContract
import com.rasalexman.stickyexample.presentation.tabs.profile.ProfilePresenter
import com.rasalexman.stickyexample.presentation.tabs.search.ISearchContract
import com.rasalexman.stickyexample.presentation.tabs.search.SearchPresenter
import com.rasalexman.stickyexample.presentation.tabs.search.SearchViewModel

/**
 * A simple [BaseHostFragment] subclass.
 */
class TabsFragment : BaseViewBindingFragment<HostFragmentTabsBinding,ITabsContract.IPresenter>(),
    ITabsContract.IView, IKodi {

    override val presenter: ITabsContract.IPresenter by immutableInstance(scope = TAB_NAVIGATOR)
    override val layoutId: Int
        get() = R.layout.host_fragment_tabs

    override val bindHandler: (View) -> HostFragmentTabsBinding
        get() = HostFragmentTabsBinding::bind

    val navHostController: NavController?
        get() = this.activity?.let { liveActivity ->
            Navigation.findNavController(liveActivity, R.id.tabsHostFragment)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        if(savedInstanceState == null) {
            import(tabsModule)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = this.childFragmentManager.findFragmentById(R.id.tabsHostFragment)
        val navController = (navHostFragment as? NavHostFragment)?.navController

        navController?.let { tabNavController ->
            NavigationUI.setupWithNavController(viewBinding.bottomNavigationView, tabNavController)
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

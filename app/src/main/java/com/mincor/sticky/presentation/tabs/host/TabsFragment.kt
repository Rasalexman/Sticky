package com.mincor.sticky.presentation.tabs.host


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.mincor.kodi.core.*
import com.mincor.sticky.R
import com.mincor.sticky.navigation.Navigators.TAB_NAVIGATOR
import com.mincor.sticky.presentation.base.BaseFragment
import com.mincor.sticky.presentation.tabs.home.HomePresenter
import com.mincor.sticky.presentation.tabs.home.IHomeContract
import com.mincor.sticky.presentation.tabs.profile.IProfileContract
import com.mincor.sticky.presentation.tabs.profile.ProfilePresenter
import com.mincor.sticky.presentation.tabs.search.ISearchContract
import com.mincor.sticky.presentation.tabs.search.SearchPresenter
import kotlinx.android.synthetic.main.host_fragment_tabs.*

/**
 * A simple [Fragment] subclass.
 */
class TabsFragment : BaseFragment<ITabsContract.IView, ITabsContract.IPresenter>(),
    ITabsContract.IView, IKodi {

    private val tabsModule = kodiModule {
        bind<ITabsContract.IPresenter>()        with single {
            TabsPresenter(
                instance()
            )
        }
        bind<IHomeContract.IPresenter>()        with single { HomePresenter(instance()) }
        bind<IProfileContract.IPresenter>()     with single { ProfilePresenter(instance()) }
        bind<ISearchContract.IPresenter>()      with single { SearchPresenter(instance()) }
    }

    init {
        kodi {
            import(tabsModule)
        }
    }

    override val presenter: ITabsContract.IPresenter by immutableInstance()
    override val layoutId: Int
        get() = R.layout.host_fragment_tabs

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.activity?.let { liveActivity ->
            val tabNavController: NavController = Navigation.findNavController(liveActivity, R.id.tabsHostFragment)
            NavigationUI.setupWithNavController(bottomNavigationView, tabNavController)

            unbind<NavController>(TAB_NAVIGATOR)
            bind<NavController>(TAB_NAVIGATOR) with provider { tabNavController }
        }
    }


}

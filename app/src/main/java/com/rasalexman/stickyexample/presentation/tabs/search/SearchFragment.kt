package com.rasalexman.stickyexample.presentation.tabs.search


import androidx.fragment.app.Fragment
import com.rasalexman.kodi.core.IKodi
import com.rasalexman.kodi.core.immutableInstance
import com.rasalexman.stickyexample.R
import com.rasalexman.stickyexample.navigation.Navigators
import com.rasalexman.stickyexample.presentation.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : BaseFragment<ISearchContract.IPresenter>(),
    ISearchContract.IView, IKodi {

    override val presenter: ISearchContract.IPresenter by immutableInstance(scope = Navigators.TAB_NAVIGATOR)
    override val layoutId: Int
        get() = R.layout.fragment_search
    }

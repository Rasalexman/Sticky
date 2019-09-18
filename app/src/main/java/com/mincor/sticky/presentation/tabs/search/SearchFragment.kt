package com.mincor.sticky.presentation.tabs.search


import androidx.fragment.app.Fragment
import com.mincor.kodi.core.IKodi
import com.mincor.kodi.core.immutableInstance
import com.mincor.sticky.R
import com.mincor.sticky.presentation.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : BaseFragment<ISearchContract.IPresenter>(),
    ISearchContract.IView, IKodi {

    override val presenter: ISearchContract.IPresenter by immutableInstance()
    override val layoutId: Int
        get() = R.layout.fragment_search
    }

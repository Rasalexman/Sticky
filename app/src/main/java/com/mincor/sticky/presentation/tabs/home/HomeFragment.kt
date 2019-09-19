package com.mincor.sticky.presentation.tabs.home


import androidx.fragment.app.Fragment
import com.mincor.kodi.core.IKodi
import com.mincor.kodi.core.instance
import com.mincor.sticky.R
import com.mincor.sticky.presentation.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment<IHomeContract.IPresenter>(),
    IHomeContract.IView, IKodi {

    override val presenter: IHomeContract.IPresenter
        get() = HomePresenter(instance())

    override val layoutId: Int
        get() = R.layout.fragment_home
}

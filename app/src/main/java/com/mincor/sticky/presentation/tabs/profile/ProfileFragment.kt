package com.mincor.sticky.presentation.tabs.profile


import androidx.fragment.app.Fragment
import com.mincor.kodi.core.IKodi
import com.mincor.kodi.core.immutableInstance
import com.mincor.sticky.R
import com.mincor.sticky.presentation.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : BaseFragment<IProfileContract.IView, IProfileContract.IPresenter>(),
    IProfileContract.IView, IKodi {

    override val presenter: IProfileContract.IPresenter by immutableInstance()
    override val layoutId: Int
        get() = R.layout.fragment_profile
}

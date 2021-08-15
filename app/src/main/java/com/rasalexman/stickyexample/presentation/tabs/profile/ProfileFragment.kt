package com.rasalexman.stickyexample.presentation.tabs.profile


import androidx.fragment.app.Fragment
import com.rasalexman.kodi.core.IKodi
import com.rasalexman.kodi.core.immutableInstance
import com.rasalexman.stickyexample.R
import com.rasalexman.stickyexample.navigation.Navigators
import com.rasalexman.stickyexample.presentation.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : BaseFragment<IProfileContract.IPresenter>(),
    IProfileContract.IView, IKodi {

    override val presenter: IProfileContract.IPresenter by immutableInstance(scope = Navigators.TAB_NAVIGATOR)
    override val layoutId: Int
        get() = R.layout.fragment_profile
}

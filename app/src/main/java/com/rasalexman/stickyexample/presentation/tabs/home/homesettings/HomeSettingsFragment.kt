package com.rasalexman.stickyexample.presentation.tabs.home.homesettings

import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.rasalexman.kodi.core.IKodi
import com.rasalexman.kodi.core.immutableInstance
import com.rasalexman.stickyexample.R
import com.rasalexman.stickyexample.navigation.Navigators
import com.rasalexman.stickyexample.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home_settings.*

class HomeSettingsFragment : BaseFragment<IHomeSettingsContract.IPresenter>(),
    IHomeSettingsContract.IView, IKodi  {

    override val layoutId: Int
        get() = R.layout.fragment_home_settings

    override val presenter: IHomeSettingsContract.IPresenter by immutableInstance(scope = Navigators.TAB_NAVIGATOR)

    override val toolbar: Toolbar?
        get() = toolbarView

    override val needBackButton: Boolean
        get() = true

    override fun onBackPressed(): Boolean {
        this.findNavController().popBackStack()
        return true
    }
}
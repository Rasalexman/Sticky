package com.mincor.sticky.presentation.tabs.home.homesettings

import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.mincor.kodi.core.IKodi
import com.mincor.kodi.core.immutableInstance
import com.mincor.sticky.R
import com.mincor.sticky.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home_settings.*

class HomeSettingsFragment : BaseFragment<HomeSettingsContract.IPresenter>(),
    HomeSettingsContract.IView, IKodi  {

    override val layoutId: Int
        get() = R.layout.fragment_home_settings

    override val presenter: HomeSettingsContract.IPresenter by immutableInstance()

    override val toolbar: Toolbar?
        get() = toolbarView

    override val needBackButton: Boolean
        get() = true

    override fun onBackPressed(): Boolean {
        this.findNavController().popBackStack()
        return true
    }
}
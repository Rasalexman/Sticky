package com.rasalexman.stickyexample.presentation.tabs.home.homesettings

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.rasalexman.kodi.core.IKodi
import com.rasalexman.kodi.core.immutableInstance
import com.rasalexman.stickyexample.R
import com.rasalexman.stickyexample.databinding.FragmentHomeBinding
import com.rasalexman.stickyexample.databinding.FragmentHomeSettingsBinding
import com.rasalexman.stickyexample.navigation.Navigators
import com.rasalexman.stickyexample.presentation.base.BaseFragment
import com.rasalexman.stickyexample.presentation.base.BaseViewBindingFragment

class HomeSettingsFragment : BaseViewBindingFragment<FragmentHomeSettingsBinding, IHomeSettingsContract.IPresenter>(),
    IHomeSettingsContract.IView, IKodi  {

    override val layoutId: Int
        get() = R.layout.fragment_home_settings

    override val presenter: IHomeSettingsContract.IPresenter by immutableInstance(scope = Navigators.TAB_NAVIGATOR)

    override val bindHandler: (View) -> FragmentHomeSettingsBinding
        get() = FragmentHomeSettingsBinding::bind

    override val toolbar: Toolbar
        get() = viewBinding.toolbarView

    override val needBackButton: Boolean
        get() = true

    override fun onBackPressed(): Boolean {
        this.findNavController().popBackStack()
        return true
    }
}
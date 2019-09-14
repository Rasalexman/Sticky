package com.mincor.sticky.presentation.start


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mincor.kodi.core.IKodi
import com.mincor.kodi.core.immutableInstance
import com.mincor.sticky.R
import com.mincor.sticky.common.YUI
import com.mincor.sticky.presentation.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class StartFragment : BaseFragment<IStartContract.IView, IStartContract.IPresenter>(),
    IStartContract.IView, IKodi {

    override val presenter: IStartContract.IPresenter by immutableInstance()
    override val layoutId: Int
        get() = R.layout.fragment_start

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("$YUI onViewCreated in StartFragment")
    }
}

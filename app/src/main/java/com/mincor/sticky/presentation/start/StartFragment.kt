package com.mincor.sticky.presentation.start


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mincor.kodi.core.IKodi
import com.mincor.kodi.core.instance
import com.mincor.sticky.R
import com.mincor.sticky.common.YUI
import com.mincor.sticky.presentation.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class StartFragment : BaseFragment<IStartContract.IStartPresenter>(), IStartContract.IStartView, IKodi {

    override val presenter: IStartContract.IStartPresenter
            get() = StartPresenter(instance())

    override val layoutId: Int
        get() = R.layout.fragment_start

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("$YUI onViewCreated in StartFragment on ${lifecycle.currentState}")
    }
}

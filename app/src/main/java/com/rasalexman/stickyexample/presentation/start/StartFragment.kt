package com.rasalexman.stickyexample.presentation.start


import androidx.fragment.app.Fragment
import com.rasalexman.kodi.core.IKodi
import com.rasalexman.kodi.core.instance
import com.rasalexman.stickyexample.R
import com.rasalexman.stickyexample.presentation.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class StartFragment : BaseFragment<IStartContract.IStartPresenter>(), IStartContract.IStartView, IKodi {

    override val presenter: IStartContract.IStartPresenter
            get() = StartPresenter(instance())

    override val layoutId: Int
        get() = R.layout.fragment_start
}

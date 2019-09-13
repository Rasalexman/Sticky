package com.mincor.sticky.presentation.start


import androidx.fragment.app.Fragment
import com.mincor.kodi.core.*
import com.mincor.sticky.R
import com.mincor.sticky.presentation.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class StartFragment : BaseFragment<IStartContract.IView, IStartContract.IPresenter>(),
    IStartContract.IView, IKodi {

    init {
        kodi {
            bind<IStartContract.IPresenter>()           with single { StartPresenter(instance(), instance()) }
        }
    }

    override val presenter: IStartContract.IPresenter by immutableInstance()
    override val layoutId: Int
        get() = R.layout.host_fragment_start
}

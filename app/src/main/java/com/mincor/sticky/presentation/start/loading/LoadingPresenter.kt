package com.mincor.sticky.presentation.start.loading

import com.mincor.kodi.core.IKodi
import com.mincor.sticky.common.YUI

class LoadingPresenter : ILoadingContract.IPresenter, IKodi {

    override fun onViewAttached(view: ILoadingContract.IView) {
        println("$YUI HELLO FROM LoadingPresenter")
    }
}
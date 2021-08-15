package com.rasalexman.stickyexample.presentation.start.loading

import com.rasalexman.kodi.core.IKodi
import com.rasalexman.sticky.common.YUI

class LoadingPresenter : ILoadingContract.IPresenter, IKodi {

    override fun onViewCreated(view: ILoadingContract.IView) {
        println("$YUI HELLO FROM LoadingPresenter")
    }
}
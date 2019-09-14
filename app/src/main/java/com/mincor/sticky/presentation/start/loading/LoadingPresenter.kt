package com.mincor.sticky.presentation.start.loading

import com.mincor.kodi.core.IKodi
import com.mincor.sticky.common.YUI
import com.rasalexman.sticky.core.BaseStickyPresenter

class LoadingPresenter : BaseStickyPresenter<ILoadingContract.IView>(),
    ILoadingContract.IPresenter, IKodi {

    override fun onViewAttached(view: ILoadingContract.IView) {
        println("$YUI HELLO FROM LoadingPresenter")
    }
}
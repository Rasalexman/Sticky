package com.rasalexman.stickyexample.presentation.tabs.home.homefilters

import com.rasalexman.sticky.common.YUI
import com.rasalexman.sticky.core.IStickyPresenter

class FilterDialogPresenter : IStickyPresenter<IFilterDialogView> {

    override fun onViewCreated(view: IFilterDialogView) {
        super.onViewCreated(view)
        println("$YUI FilterDialogPresenter created")
    }
}
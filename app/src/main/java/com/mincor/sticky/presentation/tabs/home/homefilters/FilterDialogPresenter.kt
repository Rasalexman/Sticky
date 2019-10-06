package com.mincor.sticky.presentation.tabs.home.homefilters

import com.mincor.sticky.common.YUI
import com.rasalexman.sticky.core.IStickyPresenter

class FilterDialogPresenter : IStickyPresenter<IFilterDialogView> {

    override fun onViewCreated(view: IFilterDialogView) {
        super.onViewCreated(view)
        println("$YUI FilterDialogPresenter created")
    }
}
package com.mincor.sticky.presentation.base

import com.mincor.sticky.R
import com.mincor.sticky.common.UnitHandler
import com.rasalexman.sticky.core.IStickyViewOwner

interface IBaseStickyView : IStickyViewOwner {
    fun showAlertDialog(message: Any, okTitle: Int = R.string.title_try_again, okHandler: UnitHandler? = null)
    fun showToast(message: Any, interval: Int = 0)

    fun showLoading()
    fun hideLoading()
}
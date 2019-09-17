package com.mincor.sticky.presentation.base

import com.mincor.sticky.R
import com.mincor.sticky.common.UnitHandler
import com.rasalexman.sticky.core.IStickyView

interface IBaseStickyView : IStickyView {
    fun showAlertDialog(message: Int, okTitle: Int = R.string.title_try_again, okHandler: UnitHandler? = null)
    fun showToast(messageResId: Int, interval: Int = 0)
    fun showToast(message: String, interval: Int = 0)

    fun showLoading()
    fun hideLoading()
}
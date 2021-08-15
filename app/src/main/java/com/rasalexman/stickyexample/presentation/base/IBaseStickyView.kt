package com.rasalexman.stickyexample.presentation.base

import com.rasalexman.stickyexample.R
import com.rasalexman.sticky.common.UnitHandler
import com.rasalexman.sticky.core.IStickyView

interface IBaseStickyView : IStickyView {
    fun showAlertDialog(message: Any, okTitle: Int = R.string.title_try_again, okHandler: UnitHandler? = null)
    fun showToast(message: Any, interval: Int = 0)

    fun showLoading()
    fun hideLoading()
}
package com.mincor.sticky.presentation.base

import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.mincor.sticky.R
import com.mincor.sticky.common.UnitHandler
import com.mincor.sticky.common.hide
import com.mincor.sticky.common.show
import com.rasalexman.sticky.base.BaseStickyFragment
import com.rasalexman.sticky.core.IStickyPresenter
import com.rasalexman.sticky.core.IStickyView

abstract class BaseFragment<P : IStickyPresenter<out IStickyView>> : BaseStickyFragment<P>(), INavigationHandler {

    open fun showAlertDialog(message: Int, okTitle: Int = R.string.title_try_again, okHandler: UnitHandler? = null) {
        context?.let { liveContext ->
            hideLoading()

            AlertDialog
                .Builder(liveContext)
                .setTitle(R.string.title_warning)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(okTitle) { dialogInterface, _ ->
                    dialogInterface.dismiss()
                    okHandler?.invoke()
                }
                .setNegativeButton(R.string.title_cancel) { dialogInterface, _ ->
                    dialogInterface.dismiss()
                }.show()
        }
    }

    open fun showToast(message: String, interval: Int = Toast.LENGTH_SHORT) {
        context?.let { liveContext ->
            hideLoading()
            Toast.makeText(liveContext, message, interval).show()
        }
    }
    open fun showToast(messageResId: Int, interval: Int = Toast.LENGTH_SHORT) {
        context?.let { liveContext ->
            hideLoading()
            Toast.makeText(liveContext, messageResId, interval).show()
        }
    }

    open fun showLoading() {
        view?.run {
            findViewById<ViewGroup>(R.id.contentLayout)?.hide()
            findViewById<LinearLayout>(R.id.loadingLayout)?.show()
        }
    }
    open fun hideLoading() {
        view?.run {
            findViewById<LinearLayout>(R.id.loadingLayout)?.hide()
            findViewById<ViewGroup>(R.id.contentLayout)?.show()
        }
    }

    override fun onBackPressed(): Boolean {
        val backStackEntryCount = this.childFragmentManager.backStackEntryCount
        return backStackEntryCount == 0
    }

    override fun onSupportNavigateUp(): Boolean {
        return true
    }

    override val currentNavHandler: INavigationHandler?
        get() = this
}
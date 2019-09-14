package com.mincor.sticky.presentation.base

import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.mincor.sticky.R
import com.mincor.sticky.common.UnitHandler
import com.mincor.sticky.common.hide
import com.mincor.sticky.common.show
import com.rasalexman.sticky.common.BaseStickyFragment
import com.rasalexman.sticky.core.IStickyPresenter
import com.rasalexman.sticky.core.IStickyView

abstract class BaseFragment<V : IStickyView, P : IStickyPresenter<V>> :
    BaseStickyFragment<V, P>(), INavigationHandler {

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
        view?.findViewById<LinearLayout>(R.id.loadingLayout)?.show()
    }
    open fun hideLoading() {
        view?.findViewById<LinearLayout>(R.id.loadingLayout)?.hide()
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
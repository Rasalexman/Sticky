package com.rasalexman.stickyexample.presentation.base

import android.app.Dialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.rasalexman.sticky.base.StickyFragment
import com.rasalexman.sticky.common.UnitHandler
import com.rasalexman.sticky.core.IStickyPresenter
import com.rasalexman.sticky.core.IStickyView
import com.rasalexman.stickyexample.R
import com.rasalexman.stickyexample.common.hide
import com.rasalexman.stickyexample.common.show

abstract class BaseFragment<P : IStickyPresenter<out IStickyView>> : StickyFragment<P>(),
    INavigationHandler, Toolbar.OnMenuItemClickListener {

    private var alertDialog: Dialog? = null

    protected open val toolbarTitle: String = ""
    protected open val toolbar: Toolbar? = null

    protected open val needBackButton: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar?.let(::setupToolbar)
    }

    open fun setupToolbar(toolbarView: Toolbar) {
        if(toolbarTitle.isNotEmpty()) toolbarView.title = toolbarTitle

        if(toolbarView.menu.hasVisibleItems()) {
            toolbarView.setOnMenuItemClickListener(this)
        }
        if(needBackButton) {
            toolbarView.setNavigationIcon(R.drawable.ic_arrow_back_24)
            toolbarView.setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        return true
    }


    open fun showAlertDialog(message: Any, okTitle: Int = R.string.title_try_again, okHandler: UnitHandler? = null) {

        context?.let { liveContext ->
            hideLoading()
            closeAlertDialog()

            alertDialog = AlertDialog
                .Builder(liveContext)
                .setTitle(R.string.title_warning)
                .setCancelable(false)
                .setPositiveButton(okTitle) { dialogInterface, _ ->
                    dialogInterface.dismiss()
                    okHandler?.invoke()
                }
                .setNegativeButton(R.string.title_cancel) { dialogInterface, _ ->
                    dialogInterface.dismiss()
                }.run {
                    when(message) {
                        is String -> setMessage(message)
                        is Int -> setMessage(message)
                        else -> setMessage(message.toString())
                    }
                    show()
                }
        }
    }

    open fun showToast(message: Any, interval: Int = Toast.LENGTH_SHORT) {
        context?.let { liveContext ->
            hideLoading()
            when(message) {
                is String -> Toast.makeText(liveContext, message, interval).show()
                is Int -> Toast.makeText(liveContext, message, interval).show()
                else -> Toast.makeText(liveContext, message.toString(), interval).show()
            }
        }
    }

    open fun showLoading() {
        view?.run {
            findViewById<ViewGroup>(R.id.contentLayout)?.hide()
            findViewById<ViewGroup>(R.id.loadingLayout)?.show()
        }
    }
    open fun hideLoading() {
        view?.run {
            findViewById<ViewGroup>(R.id.loadingLayout)?.hide()
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

    override fun onDestroyView() {
        toolbar?.apply {
            setNavigationOnClickListener(null)
            setOnMenuItemClickListener(null)
        }
        super.onDestroyView()
        closeAlertDialog()
    }

    private fun closeAlertDialog() {
        alertDialog?.dismiss()
        alertDialog = null
    }
}
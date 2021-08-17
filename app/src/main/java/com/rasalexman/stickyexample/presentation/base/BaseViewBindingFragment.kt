package com.rasalexman.stickyexample.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.rasalexman.stickyexample.common.hide
import com.rasalexman.stickyexample.common.show
import com.rasalexman.sticky.core.IStickyPresenter
import com.rasalexman.sticky.core.IStickyView

abstract class BaseViewBindingFragment<VB : ViewBinding, P : IStickyPresenter<out IStickyView>>:
    BaseFragment<P>() {

    abstract val bindHandler: (View) -> VB

    protected var _currentViewBinding: VB? = null
    protected val viewBinding: VB
        get() = _currentViewBinding ?: throw NullPointerException("NO BINDING CLASS")

    protected open val loadingLayout: View? = null
    protected open val contentLayout: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)?.apply {
            _currentViewBinding = bindHandler.invoke(this).also(::initBinding)
        }
    }

    protected open fun initBinding(viewBinding: VB) = Unit

    override fun showLoading() {
        contentLayout?.hide()
        loadingLayout?.show()
    }

    override fun hideLoading() {
        contentLayout?.show()
        loadingLayout?.hide()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _currentViewBinding = null
    }
}
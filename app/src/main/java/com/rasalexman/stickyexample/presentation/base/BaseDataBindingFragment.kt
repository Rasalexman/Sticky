package com.rasalexman.stickyexample.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.rasalexman.stickyexample.common.hide
import com.rasalexman.stickyexample.common.show
import com.rasalexman.sticky.core.IStickyPresenter
import com.rasalexman.sticky.core.IStickyView
import com.rasalexman.stickyexample.BR

abstract class BaseDataBindingFragment<VB : ViewDataBinding, P : IStickyPresenter<out IStickyView>>:
    BaseFragment<P>() {

    protected var _currentBinding: VB? = null
    protected val dataBinding: VB
        get() = _currentBinding ?: throw NullPointerException("NO BINDING CLASS")

    protected open val loadingLayout: View? = null
    protected open val contentLayout: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)?.apply {
            _currentBinding = DataBindingUtil.bind<VB>(this)?.apply {
                this.setVariable(BR.item, presenter)
                this.lifecycleOwner = this@BaseDataBindingFragment.viewLifecycleOwner
            }
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
        _currentBinding?.unbind()
        _currentBinding = null
    }
}
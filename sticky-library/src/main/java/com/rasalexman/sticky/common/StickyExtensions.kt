package com.rasalexman.sticky.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rasalexman.sticky.core.IStickyViewOwner

inline fun <reified VM : ViewModel> IStickyViewOwner.viewModelLazy(noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null): Lazy<VM> = lazy {
    this.viewModel<VM>(factoryProducer)
}

inline fun <reified VM : ViewModel> IStickyViewOwner.viewModel(noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null): VM {
    val factory = factoryProducer?.invoke() ?: object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.newInstance()
        }
    }
    return ViewModelProvider(this.getViewModelStoreOwner().viewModelStore, factory)[VM::class.java]
}
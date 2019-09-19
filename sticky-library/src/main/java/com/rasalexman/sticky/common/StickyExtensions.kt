package com.rasalexman.sticky.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rasalexman.sticky.core.IStickyViewOwner

inline fun <reified VM : ViewModel> IStickyViewOwner.viewModel(noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null): Lazy<VM> = lazy {
    val factory = factoryProducer?.invoke() ?: object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.newInstance()
        }
    }
    ViewModelProvider(this.getViewModelStoreOwner().viewModelStore, factory)[VM::class.java]
}
// Copyright (c) 2019 Aleksandr Minkin (sphc@yandex.ru)
//
// Permission is hereby granted, free of charge, to any person obtaining a copy of this software
// and associated documentation files (the "Software"), to deal in the Software without restriction,
// including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
// and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
// subject to the following conditions:
// The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
// WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
// IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
// WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
// THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
package com.rasalexman.sticky.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rasalexman.sticky.core.IStickyView
import com.rasalexman.sticky.core.IStickyViewOwner

/**
 * Get lazy [ViewModel] from [IStickyViewOwner]
 * @param factoryProducer noinline lambda need to return [ViewModelProvider.Factory]
 */
inline fun <reified VM : ViewModel> IStickyView.viewModelLazy(noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null): Lazy<VM> = lazy {
    this.viewModel(factoryProducer)
}

/**
 * Get [ViewModel] from [IStickyViewOwner]
 * @param factoryProducer noinline lambda need to return [ViewModelProvider.Factory]
 */
inline fun <reified VM : ViewModel> IStickyView.viewModel(noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null): VM {
    val factory = factoryProducer?.invoke() ?: object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.newInstance()
        }
    }
    return ViewModelProvider(this.getViewModelStoreOwner().viewModelStore, factory)[VM::class.java]
}


package com.rasalexman.sticky.core

import androidx.lifecycle.ViewModelStoreOwner

interface IStickyViewOwner : IStickyView {
    fun getViewModelStoreOwner(): ViewModelStoreOwner
}
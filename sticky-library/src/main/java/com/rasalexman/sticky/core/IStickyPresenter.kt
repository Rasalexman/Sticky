package com.rasalexman.sticky.core

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver

interface IStickyPresenter<V : IStickyView> : LifecycleObserver {
    var unsafeView: V?
    fun attachView(view: V, viewLifecycle: Lifecycle)
}
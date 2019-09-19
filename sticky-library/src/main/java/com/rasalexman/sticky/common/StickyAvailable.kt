package com.rasalexman.sticky.common

import androidx.lifecycle.Lifecycle

sealed class StickyAvailable(val state: Lifecycle.State) {
    object StateResumed : StickyAvailable(Lifecycle.State.RESUMED)
    object StateCreated : StickyAvailable(Lifecycle.State.CREATED)
}
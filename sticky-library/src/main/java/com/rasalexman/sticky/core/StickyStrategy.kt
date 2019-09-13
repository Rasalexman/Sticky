package com.rasalexman.sticky.core

sealed class StickyStrategy {
    object Many : StickyStrategy()
    object Single : StickyStrategy()
    data class Counter(val maxExecutionCounter: Int = 1) : StickyStrategy()
}
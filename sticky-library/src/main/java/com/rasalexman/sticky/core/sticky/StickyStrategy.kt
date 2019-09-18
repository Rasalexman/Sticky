package com.rasalexman.sticky.core.sticky

sealed class StickyStrategy {
    object Many : StickyStrategy()
    object Single : StickyStrategy()
    data class Counter @ExperimentalUnsignedTypes constructor(val maxExecutionCounter: UInt = 1u) : StickyStrategy()
}
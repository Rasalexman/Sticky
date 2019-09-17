package com.mincor.sticky.common

typealias UnitHandler = () -> Unit
typealias InHandler<T> = (T) -> Unit
typealias InSameOutHandler<T> = (T) -> T
typealias InOutHandler<T, R> = (T) -> R
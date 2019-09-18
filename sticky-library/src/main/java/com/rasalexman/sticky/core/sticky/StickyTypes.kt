package com.rasalexman.sticky.core.sticky

import com.rasalexman.sticky.core.IStickyPresenter
import com.rasalexman.sticky.core.IStickyView
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.Continuation

typealias ViewContinuationsList<V> = MutableList<Continuation<V>>
typealias ViewStickyList<V> = MutableList<ISticky<V>>

typealias ViewLifecyclerMap = MutableMap<IStickyPresenter<*>, IStickyView?>
typealias ViewAvailableMap = MutableMap<IStickyPresenter<*>, AtomicBoolean>
typealias ViewContinuationMap = MutableMap<IStickyPresenter<*>, ViewContinuationsList<IStickyView>>
typealias ViewStickiesMap = MutableMap<IStickyPresenter<*>, ViewStickyList<IStickyView>>
typealias ViewRestoreStickyMap = MutableMap<IStickyPresenter<*>, Boolean>

typealias StickyBlock<V> = V.(ISticky<V>) -> Unit
typealias StickyRemover<V> = (ISticky<V>) -> Unit
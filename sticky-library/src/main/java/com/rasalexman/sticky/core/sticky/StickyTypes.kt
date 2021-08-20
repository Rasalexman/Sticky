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
package com.rasalexman.sticky.core.sticky

import com.rasalexman.sticky.core.IStickyPresenter
import com.rasalexman.sticky.core.IStickyView
import java.lang.ref.WeakReference
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.Continuation

/**
 * [MutableList]
 */
typealias ViewContinuationsList<V> = MutableList<Continuation<V>>

/**
 * [MutableList]
 */
typealias ViewStickyList<V> = MutableList<ISticky<V>>

/**
 * [MutableMap]
 */
typealias ViewLifecyclerMap = MutableMap<IStickyPresenter<*>, WeakReference<IStickyView>?>

/**
 * [MutableMap]
 */
typealias ViewAvailableMap = MutableMap<IStickyPresenter<*>, AtomicBoolean>

/**
 * [MutableMap]
 */
typealias ViewContinuationMap = MutableMap<IStickyPresenter<*>, ViewContinuationsList<IStickyView>>

/**
 * [MutableMap]
 */
typealias ViewStickiesMap = MutableMap<IStickyPresenter<*>, ViewStickyList<IStickyView>>

/**
 * [MutableMap]
 */
typealias ViewRestoreStickyMap = MutableMap<IStickyPresenter<*>, Boolean>

/**
 * literal function with reciever
 */
typealias StickyBlock<V> = V.(ISticky<V>) -> Unit

/**
 * Remover function
 */
typealias StickyRemover<V> = (ISticky<V>) -> Unit
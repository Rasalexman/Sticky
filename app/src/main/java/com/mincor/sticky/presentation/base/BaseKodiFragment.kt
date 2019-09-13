package com.mincor.sticky.presentation.base

import com.mincor.kodi.core.IKodi
import com.rasalexman.sticky.core.IStickyPresenter
import com.rasalexman.sticky.core.IStickyView

abstract class BaseKodiFragment<P : IStickyPresenter<out IStickyView>>() :
    IKodi {

    //override val presenter: P by immutableGetter { instance<P>() }
}
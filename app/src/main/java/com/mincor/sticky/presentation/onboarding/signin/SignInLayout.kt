package com.mincor.sticky.presentation.onboarding.signin

import android.content.Context
import android.util.AttributeSet
import com.mincor.kodi.core.IKodi
import com.mincor.kodi.core.immutableInstance
import com.mincor.sticky.R
import com.rasalexman.sticky.common.BaseStickyFrameLayout

class SignInLayout : BaseStickyFrameLayout<ISignInContract.IView, ISignInContract.IPresenter> ,
        ISignInContract.IView, IKodi {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override val layoutId: Int
        get() = R.layout.layout_signin

    override val presenter: ISignInContract.IPresenter by immutableInstance()
}
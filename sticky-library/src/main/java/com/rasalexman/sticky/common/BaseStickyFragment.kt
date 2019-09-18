package com.rasalexman.sticky.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rasalexman.sticky.core.IStickyPresenter
import com.rasalexman.sticky.core.IStickyView

/**
 * Base Sticky Fragment
 */
abstract class BaseStickyFragment<P : IStickyPresenter<out IStickyView>> : Fragment() {

    /**
     *
     */
    open val safeFragment: Boolean = false

    /**
     *
     */
    abstract val presenter: P

    /**
     *
     */
    abstract val layoutId: Int

    /**
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(this is IStickyView) {
            presenter.attach(this)
        } else if(safeFragment) {
            throw StickyException.StickyCastException()
        }
    }

    /**
     *
     */
    open fun addListeners() = Unit

    /**
     *
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutId, container, false)

    /**
     *
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addListeners()
    }
}
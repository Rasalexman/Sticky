package com.rasalexman.sticky.common

import android.content.Context
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
@Suppress("UNCHECKED_CAST")
abstract class BaseStickyFragment<V : IStickyView, P : IStickyPresenter<V>> : Fragment(), IStickyView {

    /**
     *
     */
    abstract val presenter: P

    /**
     *
     */
    abstract val layoutId: Int

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter.attachView(view = this as V, viewLifecycle = lifecycle)
    }

    open fun addListeners() = Unit

    /**
     *
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutId, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addListeners()
    }
}
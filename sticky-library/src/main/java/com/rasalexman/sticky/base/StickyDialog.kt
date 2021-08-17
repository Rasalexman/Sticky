package com.rasalexman.sticky.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelStoreOwner
import com.rasalexman.sticky.core.IStickyPresenter
import com.rasalexman.sticky.core.IStickyView
import com.rasalexman.sticky.core.IStickyViewOwner

abstract class StickyDialog<P : IStickyPresenter<out IStickyView>> : DialogFragment(), IStickyViewOwner<P> {

    /**
     * on create current fragment
     * @param savedInstanceState [Bundle]
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        create(savedInstanceState)
    }

    /**
     * when need to create view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutId, container, false)

    /**
     * when view was created and dded to container
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attach()
    }

    /**
     * get [ViewModelStoreOwner]
     */
    override fun getViewModelStoreOwner(): ViewModelStoreOwner {
        return this
    }
}
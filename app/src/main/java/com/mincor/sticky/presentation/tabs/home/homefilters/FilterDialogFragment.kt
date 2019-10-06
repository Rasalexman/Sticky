package com.mincor.sticky.presentation.tabs.home.homefilters

import android.os.Bundle
import android.view.View
import com.mincor.sticky.R
import com.mincor.sticky.common.unsafeLazy
import com.rasalexman.sticky.base.StickyDialog
import kotlinx.android.synthetic.main.dialog_home_filters.*

class FilterDialogFragment : StickyDialog<FilterDialogPresenter>(), IFilterDialogView {

    override val presenter: FilterDialogPresenter by unsafeLazy { FilterDialogPresenter() }

    override val layoutId: Int
        get() = R.layout.dialog_home_filters

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleTextView.text = getString(R.string.title_filters)
        descTextView.text = getString(R.string.desc_filters)
    }

    override fun addListeners() {
        actionButton.setOnClickListener {
            this.dismiss()
        }
        cancelButton.setOnClickListener {
            this.dismiss()
        }
    }
}
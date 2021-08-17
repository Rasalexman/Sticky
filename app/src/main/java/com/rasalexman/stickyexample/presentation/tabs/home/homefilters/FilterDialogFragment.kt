package com.rasalexman.stickyexample.presentation.tabs.home.homefilters

import android.os.Bundle
import android.view.View
import com.rasalexman.sticky.base.StickyDialog
import com.rasalexman.stickyexample.R
import com.rasalexman.stickyexample.common.unsafeLazy
import com.rasalexman.stickyexample.databinding.DialogHomeFiltersBinding

class FilterDialogFragment : StickyDialog<FilterDialogPresenter>(), IFilterDialogView {

    override val presenter: FilterDialogPresenter by unsafeLazy { FilterDialogPresenter() }

    override val layoutId: Int
        get() = R.layout.dialog_home_filters

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = DialogHomeFiltersBinding.bind(view)
        binding.apply {
            actionButton.setOnClickListener {
                this@FilterDialogFragment.dismiss()
            }
            cancelButton.setOnClickListener {
                this@FilterDialogFragment.dismiss()
            }
            titleTextView.text = getString(R.string.title_filters)
            descTextView.text = getString(R.string.desc_filters)
        }
    }
}
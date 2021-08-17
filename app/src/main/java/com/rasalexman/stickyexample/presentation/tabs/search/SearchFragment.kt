package com.rasalexman.stickyexample.presentation.tabs.search


import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.rasalexman.kodi.core.IKodi
import com.rasalexman.kodi.core.immutableInstance
import com.rasalexman.stickyexample.R
import com.rasalexman.stickyexample.databinding.FragmentSearchBinding
import com.rasalexman.stickyexample.navigation.Navigators
import com.rasalexman.stickyexample.presentation.base.BaseViewBindingFragment

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : BaseViewBindingFragment<FragmentSearchBinding, ISearchContract.IPresenter>(),
    ISearchContract.IView, IKodi, SearchView.OnQueryTextListener {

    override val presenter: ISearchContract.IPresenter by immutableInstance(scope = Navigators.TAB_NAVIGATOR)
    override val layoutId: Int
        get() = R.layout.fragment_search

    override val bindHandler: (View) -> FragmentSearchBinding
        get() = FragmentSearchBinding::bind

    override fun initBinding(viewBinding: FragmentSearchBinding) {
        super.initBinding(viewBinding)
        viewBinding.searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        presenter.searchByQuery(newText.orEmpty())
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

}

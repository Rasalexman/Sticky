package com.rasalexman.stickyexample.presentation.tabs.search

import androidx.lifecycle.Observer
import com.rasalexman.coroutinesmanager.ICoroutinesManager
import com.rasalexman.coroutinesmanager.launchOnUITryCatch
import com.rasalexman.kodi.core.IKodi
import com.rasalexman.stickyexample.common.SResult
import com.rasalexman.stickyexample.common.YUI
import com.rasalexman.stickyexample.R

class SearchPresenter(
    private val searchViewModel: SearchViewModel,
    coroutinesManager: ICoroutinesManager
) : ISearchContract.IPresenter, IKodi, ICoroutinesManager by coroutinesManager {

    private val searchObserver = Observer(::searchLiveDataReducer)

    override fun onViewCreated(view: ISearchContract.IView) = launchOnUITryCatch(
        tryBlock = {
            println("$YUI HELLO THIS IS A SearchPresenter")
            searchViewModel.getSearchLiveData().observe(view(), searchObserver)
        }, catchBlock = {
            println("$YUI catchBlock in SearchPresenter")
            view().showToast(R.string.error_unexpected)
        }
    )

    private fun searchLiveDataReducer(result: SResult<List<SearchUiModel>>) = launchOnUITryCatch(
        tryBlock = {
            when(result) {
                is SResult.Loading -> view().showLoading()
                //is SResult.Success -> showItems(result.data)
                is SResult.Error -> view().showToast(result.message)
            }
        }, catchBlock = {
            view().showToast(R.string.error_reducer_unexpected)
        }
    )

    override fun searchByQuery(query: String) {
        searchViewModel.onQueryListener(query)
    }

    override fun onViewDestroyed(view: ISearchContract.IView) {
        searchViewModel.getSearchLiveData().removeObserver(searchObserver)
    }
}
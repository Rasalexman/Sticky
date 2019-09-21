package com.mincor.sticky.presentation.tabs.search

import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.mincor.kodi.core.IKodi
import com.mincor.sticky.R
import com.mincor.sticky.common.SResult
import com.mincor.sticky.common.YUI
import com.mincor.sticky.navigation.tabNavigator
import com.rasalexman.coroutinesmanager.ICoroutinesManager
import com.rasalexman.coroutinesmanager.launchOnUITryCatch

class SearchPresenter(
    private val searchViewModel: SearchViewModel,
    coroutinesManager: ICoroutinesManager
) : ISearchContract.IPresenter, IKodi, ICoroutinesManager by coroutinesManager {

    private val tabNavigator: NavController by tabNavigator()
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

    override fun onViewDestroyed(view: ISearchContract.IView) {
        searchViewModel.getSearchLiveData().removeObserver(searchObserver)
    }
}
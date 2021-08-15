package com.rasalexman.stickyexample.presentation.tabs.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.rasalexman.sticky.common.SResult
import com.rasalexman.sticky.common.SearchLiveData
import com.rasalexman.sticky.common.loading
import kotlinx.coroutines.Dispatchers

class SearchViewModel : ViewModel() {

    private val searchListLiveData: SearchLiveData by lazy {
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(loading())
            emit(SResult.Success<List<SearchUiModel>>(listOf()))
        }
    }

    fun getSearchLiveData(): SearchLiveData = searchListLiveData
}
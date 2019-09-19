package com.mincor.sticky.presentation.tabs.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.mincor.sticky.common.SResult
import com.mincor.sticky.common.SearchLiveData
import com.mincor.sticky.common.loading
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
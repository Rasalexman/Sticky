package com.rasalexman.stickyexample.presentation.tabs.search

import androidx.lifecycle.*
import com.rasalexman.stickyexample.common.SResult
import com.rasalexman.sticky.common.SearchLiveData
import com.rasalexman.stickyexample.common.loading
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

class SearchViewModel : ViewModel() {

    protected val searchQuery = MutableLiveData<String>()

    private val searchListLiveData: SearchLiveData by lazy {
        searchQuery.switchMap {
            liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
                emit(loading())
                delay(1200L)
                emit(SResult.Success<List<SearchUiModel>>(listOf()))
            }
        }
    }

    fun getSearchLiveData(): SearchLiveData = searchListLiveData

    fun onQueryListener(query: String) {

    }
}
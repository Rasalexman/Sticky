package com.rasalexman.stickyexample.presentation.tabs.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.rasalexman.sticky.common.HomeLiveData
import com.rasalexman.sticky.common.SResult
import com.rasalexman.sticky.common.loading
import kotlinx.coroutines.Dispatchers

class HomeViewModel : ViewModel() {

    private val homeListLiveData: HomeLiveData by lazy {
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(loading())
            emit(SResult.Success<List<HomeUiModel>>(listOf()))
        }
    }

    fun getHomeLiveData(): HomeLiveData = homeListLiveData
}
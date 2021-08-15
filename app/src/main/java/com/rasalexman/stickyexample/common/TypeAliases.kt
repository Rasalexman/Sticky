package com.rasalexman.sticky.common

import androidx.lifecycle.LiveData
import com.rasalexman.stickyexample.presentation.tabs.home.HomeUiModel
import com.rasalexman.stickyexample.presentation.tabs.search.SearchUiModel

typealias UnitHandler = () -> Unit
typealias InHandler<T> = (T) -> Unit
typealias InSameOutHandler<T> = (T) -> T
typealias InOutHandler<T, R> = (T) -> R

typealias SearchLiveData = LiveData<SResult<List<SearchUiModel>>>
typealias HomeLiveData = LiveData<SResult<List<HomeUiModel>>>
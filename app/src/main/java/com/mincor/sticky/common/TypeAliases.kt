package com.mincor.sticky.common

import androidx.lifecycle.LiveData
import com.mincor.sticky.presentation.tabs.home.HomeUiModel
import com.mincor.sticky.presentation.tabs.search.SearchUiModel

typealias UnitHandler = () -> Unit
typealias InHandler<T> = (T) -> Unit
typealias InSameOutHandler<T> = (T) -> T
typealias InOutHandler<T, R> = (T) -> R

typealias SearchLiveData = LiveData<SResult<List<SearchUiModel>>>
typealias HomeLiveData = LiveData<SResult<List<HomeUiModel>>>
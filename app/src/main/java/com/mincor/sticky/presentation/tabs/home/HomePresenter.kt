package com.mincor.sticky.presentation.tabs.home

import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.mincor.kodi.core.IKodi
import com.mincor.sticky.R
import com.mincor.sticky.common.SResult
import com.mincor.sticky.common.YUI
import com.mincor.sticky.navigation.tabNavigator
import com.rasalexman.coroutinesmanager.ICoroutinesManager
import com.rasalexman.coroutinesmanager.launchOnUITryCatch
import com.rasalexman.sticky.common.viewModel
import com.rasalexman.sticky.common.viewModelLazy

class HomePresenter(
    coroutinesManager: ICoroutinesManager
) : IHomeContract.IPresenter, IKodi, ICoroutinesManager by coroutinesManager {

    private val tabNavigator: NavController by tabNavigator()

    private val homeObserver = Observer(::homeViewModelReducer)

    override fun onViewCreated(view: IHomeContract.IView) = launchOnUITryCatch(
        tryBlock = {
            println("$YUI HELLO THIS IS A ${this@HomePresenter}")
            view().viewModel<HomeViewModel>().getHomeLiveData().observe(view, homeObserver)
        }, catchBlock = {
            view().showToast(R.string.error_unexpected)
        }
    )

    private fun homeViewModelReducer(result: SResult<List<HomeUiModel>>) = launchOnUITryCatch(
        tryBlock = {
            when(result) {
                is SResult.Loading -> view().showLoading()
                //is SResult.Success -> showItems(result.data)
                is SResult.Error -> view().showToast(result.message)
            }
        }, catchBlock = {

        })


    override fun onViewDestroyed(view: IHomeContract.IView) {
        val homeViewModel: HomeViewModel by view.viewModelLazy()
        homeViewModel.getHomeLiveData().removeObserver(homeObserver)
        cleanup()
    }
}
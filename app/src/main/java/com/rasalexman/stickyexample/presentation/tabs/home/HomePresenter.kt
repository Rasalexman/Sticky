package com.rasalexman.stickyexample.presentation.tabs.home

import androidx.lifecycle.Observer
import com.rasalexman.coroutinesmanager.ICoroutinesManager
import com.rasalexman.coroutinesmanager.launchOnUITryCatch
import com.rasalexman.kodi.core.IKodi
import com.rasalexman.stickyexample.common.SResult
import com.rasalexman.sticky.common.viewModel
import com.rasalexman.sticky.common.viewModelLazy
import com.rasalexman.sticky.core.IStickyView
import com.rasalexman.stickyexample.R
import com.rasalexman.stickyexample.common.YUI

class HomePresenter(
    coroutinesManager: ICoroutinesManager
) : IHomeContract.IPresenter, IKodi, ICoroutinesManager by coroutinesManager {

    private val homeObserver = Observer(::homeViewModelReducer)

    override fun onFirstAttach(view: IStickyView) {
        launchOnUITryCatch(
            tryBlock = {
                println("$YUI HELLO THIS IS A ${this@HomePresenter}")
                view().viewModel<HomeViewModel>().getHomeLiveData().observe(view, homeObserver)
            }, catchBlock = {
                view().showToast(R.string.error_unexpected)
            }
        )
    }

    private fun homeViewModelReducer(result: SResult<List<HomeUiModel>>) = launchOnUITryCatch(
        tryBlock = {
            when (result) {
                is SResult.Loading -> view().showLoading()
                //is SResult.Success -> showItems(result.data)
                is SResult.Error -> view().showToast(result.message)
            }
        }, catchBlock = {

        })


    override fun onViewDestroyed(view: IHomeContract.IView) {
        val homeViewModel: HomeViewModel by view.viewModelLazy()
        homeViewModel.getHomeLiveData().removeObservers(view)
        cleanup()
    }
}
package com.rasalexman.stickyexample.presentation.tabs.home.layout

class HomeLayoutPresenter : IHomeLayoutContract.IPresenter {

    override fun onViewCreated(view: IHomeLayoutContract.IView) {
        super.onViewCreated(view)
        println("-----> HomeLayoutPresenter created")
    }
}
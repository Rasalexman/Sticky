package com.rasalexman.stickyexample.presentation.base

interface INavigationHandler {
    val currentNavHandler: INavigationHandler?
    fun onSupportNavigateUp(): Boolean
    fun onBackPressed(): Boolean
}
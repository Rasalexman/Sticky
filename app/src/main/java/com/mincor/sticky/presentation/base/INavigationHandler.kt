package com.mincor.sticky.presentation.base

interface INavigationHandler {
    fun onSupportNavigateUp(): Boolean
    fun onBackPressed(): Boolean
}
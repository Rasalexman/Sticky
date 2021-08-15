package com.rasalexman.stickyexample.data.local

interface IUserAccount {
    var name: String
    var email: String
    var token: String
}

fun IUserAccount.isRegistered(): Boolean {
    return this.token.isNotEmpty()
}
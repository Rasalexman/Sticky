package com.mincor.sticky.data.local

import android.content.Context
import com.chibatching.kotpref.KotprefModel

class UserAccount(context: Context) : KotprefModel(context), IUserAccount {
    override var name: String by stringPref("")
    override var email: String by stringPref("")
    override var token: String by stringPref("")
}
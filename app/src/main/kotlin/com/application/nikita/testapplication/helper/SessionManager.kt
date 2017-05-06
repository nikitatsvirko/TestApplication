package com.application.nikita.testapplication.helper

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

class SessionManager(context: Context) {

    var sharedPrefs: SharedPreferences? = null
    var editor: Editor
    var _context: Context = context

    private val PREF_NAME: String = "ApplicationUserLogin"
    private val KEY_IS_LOGGED_IN: String = "isLoggedIn"
    val PRIVATE_MODE: Int = 0

    init {
        sharedPrefs = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = sharedPrefs?.edit() as Editor
    }

    fun setLogin(isLoggedIn: Boolean) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
        editor.commit()
    }

    fun isLoggedIn(): Boolean = sharedPrefs?.getBoolean(KEY_IS_LOGGED_IN, false)!!
}
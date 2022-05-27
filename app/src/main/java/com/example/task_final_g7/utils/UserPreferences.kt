package com.example.task_final_g7.utils

import android.content.Context


internal class UserPreferences(context: Context) {

    companion object {
        const val PREFS_NAME = "user_pref"
        const val USER_LOGIN = "user_login"
        const val LOGIN_STATUS = "login_status"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setLoggedInUser(username: String) {
        val editor = preferences.edit()
        editor.putString(USER_LOGIN, username)
        editor.apply()
    }

    fun getLoggedInUser() =
        preferences.getString(USER_LOGIN, "")

    fun setLoggedInStatus(status: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(LOGIN_STATUS, status)
        editor.apply()
    }

    fun getLoggedInStatus() =
        preferences.getBoolean(LOGIN_STATUS, false)

    fun clearLoggedInUser() {
        val editor = preferences.edit()
        editor.remove(USER_LOGIN)
        editor.remove(LOGIN_STATUS)
        editor.apply()
    }
}
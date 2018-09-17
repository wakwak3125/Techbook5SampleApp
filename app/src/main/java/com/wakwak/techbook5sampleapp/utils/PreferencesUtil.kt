package com.wakwak.techbook5sampleapp.utils

import android.content.SharedPreferences

class PreferencesUtil(private val prefs: SharedPreferences) {


    fun putToken(token: String) = prefs.edit().putString("token", token).apply()

    fun getToken(): String = prefs.getString("token", "") ?: ""
}

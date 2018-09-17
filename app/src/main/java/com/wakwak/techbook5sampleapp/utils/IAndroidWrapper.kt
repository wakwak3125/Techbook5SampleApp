package com.wakwak.techbook5sampleapp.utils

import android.text.TextUtils

interface IAndroidWrapper {

    fun getString(resourceId: Int): String

    fun isEmpty(string: String?): Boolean = TextUtils.isEmpty(string)
}

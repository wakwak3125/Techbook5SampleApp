package com.wakwak.techbook5sampleapp.utils

import android.content.Context

class AndroidWrapper(private val context: Context) : IAndroidWrapper {

    override fun getString(resourceId: Int): String {
        return context.getString(resourceId)
    }
}

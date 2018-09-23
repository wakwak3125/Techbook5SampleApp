package com.wakwak.techbook5sampleapp

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi

const val DEFAULT_MESSAGE = "Default Message"

inline fun <reified T> T.toJson(moshi: Moshi): String = moshi.adapter(T::class.java).toJson(this)

@JsonClass(generateAdapter = true)
data class SimpleDataClass(val id: Long, val message: String? = DEFAULT_MESSAGE, val name: String?)

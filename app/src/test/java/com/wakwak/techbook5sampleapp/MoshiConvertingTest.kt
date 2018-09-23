package com.wakwak.techbook5sampleapp

import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.json.JSONException
import org.json.JSONObject
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MoshiConvertingTest {

    lateinit var moshi: Moshi

    @Before
    fun setUp() {
        moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Test
    @Throws(Exception::class)
    fun convertNonNullValueTest() {
        val id = 1L
        val message = "Hello, Moshi :)"
        val name = "wakwak3125"
        val json = JSONObject().apply {
            putOpt("id", id)
            putOpt("message", message)
            putOpt("name", name)
        }.toString()

        val dataClass = moshi.adapter(SimpleDataClass::class.java).fromJson(json)

        assertThat(dataClass?.id, `is`(id))
        assertThat(dataClass?.message, `is`(message))
        assertThat(dataClass?.name, `is`(name))
    }

    @Test
    @Throws(Exception::class)
    fun convertNullIdFailTest() {
        val id = JSONObject.NULL
        val message = JSONObject.NULL
        val name = null

        val json = JSONObject().apply {
            putOpt("id", id)
            putOpt("message", message)
            putOpt("name", name)
        }.toString()
        try {
            moshi.adapter(SimpleDataClass::class.java).fromJson(json)
        } catch (e: JsonDataException) {
            assertThat(e.message, `is`("Unexpected null at \$.id"))
        }
    }

    @Test
    @Throws(Exception::class)
    fun convertNullTest() {
        val id = 1L
        val message = JSONObject.NULL
        val name: String? = null

        val json = JSONObject().apply {
            putOpt("id", id)
            putOpt("message", message)
            putOpt("name", name)
        }.toString()

        val dataClass = moshi.adapter(SimpleDataClass::class.java).fromJson(json)

        assertThat(dataClass?.id, `is`(id))
        assertThat(dataClass?.message, nullValue())
        assertThat(dataClass?.name, nullValue())
    }

    @Test
    @Throws(Exception::class)
    fun convertNullDefaultValueTest() {
        val id = 1L
        val message = null
        val name: String? = null

        val json = JSONObject().apply {
            putOpt("id", id)
            putOpt("message", message)
            putOpt("name", name)
        }.toString()

        val dataClass = moshi.adapter(SimpleDataClass::class.java).fromJson(json)

        assertThat(dataClass?.id, `is`(id))
        assertThat(dataClass?.message, `is`(DEFAULT_MESSAGE))
        assertThat(dataClass?.name, nullValue())
    }
}

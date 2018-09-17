package com.wakwak.techbook5sampleapp.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class GitHubUser(
        @PrimaryKey
        val id: Long,
        @ColumnInfo(name = "user_name")
        @Json(name = "login")
        val userName: String,
        @ColumnInfo(name = "avatar_url")
        @Json(name = "avatar_url")
        val avatarUrl: String,
        @ColumnInfo(name = "bio")
        @Json(name = "bio")
        val bio: String
)

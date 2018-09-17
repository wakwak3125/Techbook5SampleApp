package com.wakwak.techbook5sampleapp.model.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wakwak.techbook5sampleapp.model.data.store.GitHubUserStore

@Database(entities = [GitHubUser::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gitHubUserStore(): GitHubUserStore
}

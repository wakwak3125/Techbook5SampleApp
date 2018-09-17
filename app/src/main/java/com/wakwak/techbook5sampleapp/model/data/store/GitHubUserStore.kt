package com.wakwak.techbook5sampleapp.model.data.store

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wakwak.techbook5sampleapp.model.data.GitHubUser
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface GitHubUserStore {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createGitHubUser(gitHubUser: GitHubUser)

    @Query("SELECT * FROM GitHubUser WHERE user_name = :userName")
    fun find(userName: String): Single<GitHubUser>

    @Query("SELECT * FROM GitHubUser")
    fun findAll(): Flowable<List<GitHubUser>>
}

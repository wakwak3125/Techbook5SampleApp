package com.wakwak.techbook5sampleapp.api

import com.wakwak.techbook5sampleapp.model.data.GitHubUser
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {

    @GET("/users/{userName}")
    fun getGitHubUser(@Path("userName") userName: String): Single<GitHubUser>
}

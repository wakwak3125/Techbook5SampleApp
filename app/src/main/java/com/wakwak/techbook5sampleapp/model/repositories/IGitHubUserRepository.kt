package com.wakwak.techbook5sampleapp.model.repositories

import com.wakwak.techbook5sampleapp.model.data.GitHubUser
import io.reactivex.Flowable

interface IGitHubUserRepository {

    fun getGitHubUser(userName: String): Flowable<GitHubUser>
}

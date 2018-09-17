package com.wakwak.techbook5sampleapp.model.usecases

import com.wakwak.techbook5sampleapp.model.data.GitHubUser
import io.reactivex.Flowable
import io.reactivex.Maybe

interface IGitHubUserPageUseCase {

    fun getGitHubUser(userName: String?): Flowable<GitHubUser>

    fun getGitHubUsers(): Flowable<List<GitHubUser>>
}

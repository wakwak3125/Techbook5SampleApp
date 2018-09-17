package com.wakwak.techbook5sampleapp.model.usecases

import com.wakwak.techbook5sampleapp.model.data.GitHubUser
import io.reactivex.Flowable

interface IGitHubUserPageUseCase {

    fun getGitHubUser(userName: String?): Flowable<GitHubUser>
}

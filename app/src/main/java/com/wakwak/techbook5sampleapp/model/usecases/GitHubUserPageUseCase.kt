package com.wakwak.techbook5sampleapp.model.usecases

import com.wakwak.techbook5sampleapp.model.data.GitHubUser
import com.wakwak.techbook5sampleapp.model.repositories.IGitHubUserRepository
import io.reactivex.Flowable
import io.reactivex.Maybe

class GitHubUserPageUseCase(private val githubUserRepository: IGitHubUserRepository)
    : IGitHubUserPageUseCase {

    override fun getGitHubUser(userName: String?): Flowable<GitHubUser> {
        if (userName == null || userName.isEmpty()) {
            return Flowable.error(EmptyUserNameException())
        }
        return githubUserRepository.getGitHubUser(userName)
    }

    override fun getGitHubUsers(): Flowable<List<GitHubUser>> {
        return githubUserRepository.getGitHubUsers()
    }

    class EmptyUserNameException
        : IllegalArgumentException("UserName must be provided")
}

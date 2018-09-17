package com.wakwak.techbook5sampleapp.model.repositories

import com.wakwak.techbook5sampleapp.api.GitHubService
import com.wakwak.techbook5sampleapp.model.data.GitHubUser
import com.wakwak.techbook5sampleapp.model.data.store.GitHubUserStore
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers

/**
 * @param api GitHubApiのインターフェイスを提供する
 * @param store ローカルのGitHubUserのインターフェイスを提供している
 *
 * */
class GitHubUserRepository(private val api: GitHubService,
                           private val store: GitHubUserStore) : IGitHubUserRepository {

    override fun getGitHubUser(userName: String): Flowable<GitHubUser> {
        return Maybe.concat(
                store.find(userName).subscribeOn(Schedulers.io())
                        .toMaybe()
                        .onErrorComplete(),
                api.getGitHubUser(userName).toMaybe().map {
                    store.createGitHubUser(it)
                    return@map it
                }
        )
    }

    override fun getGitHubUsers(): Flowable<List<GitHubUser>> {
        return store.findAll().subscribeOn(Schedulers.io())
    }
}

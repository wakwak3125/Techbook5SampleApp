package com.wakwak.techbook5sampleapp.view.epoxy_controllers

import com.airbnb.epoxy.TypedEpoxyController
import com.wakwak.techbook5sampleapp.itemFetchedUser
import com.wakwak.techbook5sampleapp.model.data.GitHubUser
import com.wakwak.techbook5sampleapp.presentation.presenters.GitHubUserPresenter

class GitHubUserController(private val presenter: GitHubUserPresenter)
    : TypedEpoxyController<List<GitHubUser>>() {

    override fun buildModels(data: List<GitHubUser>?) {
        data?.forEach {
            itemFetchedUser {
                id(it.id)
                userName(it.userName)
                avatarUrl(it.avatarUrl)
                presenter(presenter)
            }
        }
    }
}

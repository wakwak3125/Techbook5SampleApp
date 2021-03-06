package com.wakwak.techbook5sampleapp.view.view

import com.wakwak.techbook5sampleapp.model.data.GitHubUser
import com.wakwak.techbook5sampleapp.presentation.binding_data.GitHubUserBindableData

interface IGitHubUserView : IView<GitHubUserBindableData> {

    fun showSearchDialog()

    fun setUpDrawerItems(users: List<GitHubUser>)

    fun closeDrawer()
}

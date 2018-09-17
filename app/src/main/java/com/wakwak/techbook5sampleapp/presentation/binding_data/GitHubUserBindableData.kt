package com.wakwak.techbook5sampleapp.presentation.binding_data

data class GitHubUserBindableData(
        var userName: String = "",
        var avatarUrl: String = "",
        var bio: String = "",
        var loadingVisibility: Boolean = false
) : IBindable

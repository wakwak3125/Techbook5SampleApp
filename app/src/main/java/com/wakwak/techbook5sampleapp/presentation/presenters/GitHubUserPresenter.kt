package com.wakwak.techbook5sampleapp.presentation.presenters

import com.wakwak.techbook5sampleapp.R
import com.wakwak.techbook5sampleapp.model.data.GitHubUser
import com.wakwak.techbook5sampleapp.model.usecases.GitHubUserPageUseCase
import com.wakwak.techbook5sampleapp.model.usecases.IGitHubUserPageUseCase
import com.wakwak.techbook5sampleapp.presentation.binding_data.GitHubUserBindableData
import com.wakwak.techbook5sampleapp.utils.IAndroidWrapper
import com.wakwak.techbook5sampleapp.view.view.IGitHubUserView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class GitHubUserPresenter(private val useCase: IGitHubUserPageUseCase,
                          wrapper: IAndroidWrapper)
    : Presenter<IGitHubUserView>(wrapper) {

    private lateinit var view: IGitHubUserView
    private val disposables: CompositeDisposable = CompositeDisposable()
    private val bindableData: GitHubUserBindableData = GitHubUserBindableData()

    override fun attach(v: IGitHubUserView) {
        this.view = v
    }

    override fun detach() {
        disposables.clear()
    }

    fun showGitHubUserByUserName(userName: String?) {
        setLoadingVisibility(true)
        val disposable = useCase.getGitHubUser(userName)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate { setLoadingVisibility(false) }
                .subscribe(
                        { apply(it) },
                        { showErrorMessage(it) })
        disposables.add(disposable)
    }

    private fun apply(gitHubUser: GitHubUser) {
        view.bind(with(bindableData) {
            bindableData.userName = gitHubUser.userName
            bindableData.avatarUrl = gitHubUser.avatarUrl
            bindableData.bio = if (wrapper.isEmpty(gitHubUser.bio)) {
                getString(R.string.msg_no_bio)
            } else {
                gitHubUser.bio
            }
            bindableData.loadingVisibility = false
            return@with this
        })
    }

    private fun setLoadingVisibility(visibility: Boolean) {
        view.bind(with(bindableData) {
            bindableData.loadingVisibility = visibility
            return@with this
        })
    }

    private fun showErrorMessage(e: Throwable) {
        val defaultErrorMessage = getString(R.string.error_failed_to_fetch_user)
        when (e) {
            is GitHubUserPageUseCase.EmptyUserNameException -> {
                view.showMessage(e.message ?: defaultErrorMessage)
            }
            else -> {
                view.showMessage(defaultErrorMessage)
            }
        }
    }
}

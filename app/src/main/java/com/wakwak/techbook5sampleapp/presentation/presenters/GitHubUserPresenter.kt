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
import io.reactivex.rxkotlin.addTo

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
        useCase.getGitHubUser(userName)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate { setLoadingVisibility(false) }
                .subscribe(
                        { apply(it) },
                        { showErrorMessage(it) })
                .addTo(disposables)
    }

    fun setUpDrawerItems() {
        useCase.getGitHubUsers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { view.setUpDrawerItems(it) },
                        { /*no-op*/ },
                        { /*no-op*/ })
                .addTo(disposables)
    }

    private fun apply(gitHubUser: GitHubUser) {
        view.bind(bindableData.apply {
            bindableData.userName = gitHubUser.userName
            bindableData.avatarUrl = gitHubUser.avatarUrl
            bindableData.bio = if (gitHubUser.bio != null && gitHubUser.bio.isNotEmpty()) {
                gitHubUser.bio
            } else {
                getString(R.string.msg_no_bio)
            }
            bindableData.loadingVisibility = false
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

    fun onClickFetchedUser(userName: String) {
        showGitHubUserByUserName(userName)
        view.closeDrawer()
    }

    fun onClickMenuItem(itemId: Int) = when (itemId) {
        R.id.search -> {
            view.showSearchDialog()
            true
        }
        else -> false
    }
}

package com.wakwak.techbook5sampleapp.presentation.presenters

import com.wakwak.techbook5sampleapp.view.view.IView

interface IPresenter<V : IView<*>> {

    fun attach(v: V)

    fun detach()
}

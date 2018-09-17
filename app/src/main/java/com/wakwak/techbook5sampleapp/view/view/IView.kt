package com.wakwak.techbook5sampleapp.view.view

import com.wakwak.techbook5sampleapp.presentation.binding_data.IBindable

interface IView<in B : IBindable> {

    fun bind(bindable: B)

    fun showMessage(message: String)
}

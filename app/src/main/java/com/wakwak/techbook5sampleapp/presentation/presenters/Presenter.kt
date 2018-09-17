package com.wakwak.techbook5sampleapp.presentation.presenters

import com.wakwak.techbook5sampleapp.utils.IAndroidWrapper
import com.wakwak.techbook5sampleapp.view.view.IView

abstract class Presenter<V : IView<*>>(val wrapper: IAndroidWrapper) : IPresenter<V> {

    fun getString(resourceId: Int): String {
        return wrapper.getString(resourceId)
    }
}

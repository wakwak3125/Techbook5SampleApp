package com.wakwak.techbook5sampleapp.presentation

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("avatarUrl")
    fun ImageView.setAvatarImage(avatarUrl: String) {
        if (avatarUrl.isEmpty()) return
        Picasso.get().load(avatarUrl).into(this)
    }
}

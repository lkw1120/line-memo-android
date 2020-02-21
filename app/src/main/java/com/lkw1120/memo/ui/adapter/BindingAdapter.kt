package com.lkw1120.memo.ui.adapter

import androidx.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.lkw1120.memo.R

/*
    Glide
    https://bumptech.github.io/glide/
 */
@BindingAdapter("thumbnail")
fun setThumbnail(view: ImageView, url: String?) {
    Glide.with(view.context)
        .load(url)
        .centerCrop()
        .error(R.drawable.ic_photo_24px)
        .into(view)
}

@BindingAdapter("imageSrc")
fun setImage(view: ImageView, url: String?) {
    Glide.with(view.context)
        .load(url)
        //.centerCrop()
        //.error(R.drawable.ic_add_a_photo_24px)
        .into(view)
}

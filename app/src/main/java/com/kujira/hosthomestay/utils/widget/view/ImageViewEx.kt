package com.kujira.hosthomestay.utils.widget.view

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


/**
 * Created by OpenYourEyes on 11/20/20
 */
@BindingAdapter("url")
fun setImageViewResource(imageView: ImageView, url: String) {
    Glide.with(imageView.context).load(url)
        .into(imageView)
}
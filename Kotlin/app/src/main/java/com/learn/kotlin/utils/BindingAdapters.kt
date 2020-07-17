package com.learn.kotlin.utils

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.learn.kotlin.R


/**
 * Uses the Glide library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_image)
                    .error(R.drawable.no_image)
            )
            .into(imgView)
    }
}

/**
 * Binding adapter used to hide the progress spinner once data is available.
 */
@BindingAdapter("isNetworkError", "datalist")
fun hideIfNetworkError(view: View, isNetWorkError: Boolean, datalist: Any?) {
    view.visibility = if (datalist != null) View.GONE else View.VISIBLE

    if(isNetWorkError) {
        view.visibility = View.GONE
    }
}
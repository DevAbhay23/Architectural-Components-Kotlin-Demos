package com.learn.kotlin

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.learn.kotlin.videos.VideoApiStatus

///**
// * When there is no Mars property data (data is null), hide the [RecyclerView], otherwise show it.
// */
//@BindingAdapter("listData")
//fun bindRecyclerView(recyclerView: RecyclerView, data: List<DatabaseNews>?) {
//    val adapter = recyclerView.adapter as NewsListAdapter
//    adapter.submitList(data)
//}

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
 * This binding adapter displays the [VideoApiStatus] of the network request in an image view.  When
 * the request is loading, it displays a loading_animation.  If the request has an error, it
 * displays a broken image to reflect the connection error.  When the request is finished, it
 * hides the image view.
 */
@BindingAdapter("videoApiStatus")
fun bindStatus(statusImageView: ImageView, status: VideoApiStatus?) {
    when (status) {
        VideoApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_image)
        }
        VideoApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.no_image)
        }
        VideoApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}
package com.learn.kotlin.videos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.learn.kotlin.R
import com.learn.kotlin.databinding.ItemVideoBinding
import com.learn.kotlin.domain.VideoStoryModel


class VideosListAdapter(private val onClickListener: OnClickListener) : RecyclerView.Adapter<VideosListAdapter.VideoViewHolder>() {

    /**
     * The video list that our Adapter will show
     */
    var video: List<VideoStoryModel> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {

        holder.binding.also {
            it.video = video[position]
            holder.itemView.setOnClickListener {
                onClickListener.onClick(video[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder.from(parent)
    }

    override fun getItemCount() = video.size

    class VideoViewHolder private constructor(val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): VideoViewHolder {
                val withDataBinding: ItemVideoBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_video,
                    parent,
                    false
                )
                return VideoViewHolder(withDataBinding)
            }
        }
    }

    class OnClickListener(val clickListener: (videoObj:VideoStoryModel) -> Unit) {
        fun onClick(videoObj:VideoStoryModel) = clickListener(videoObj)
    }
}

package com.learn.kotlin.videos_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.learn.kotlin.R
import com.learn.kotlin.databinding.FragmentVideosDetailsBinding


class VideosDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentVideosDetailsBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_videos_details, container, false
            )
        binding.lifecycleOwner = viewLifecycleOwner
        val vidoesObj = VideosDetailsFragmentArgs.fromBundle(arguments!!).selectedProperty
        val application = requireNotNull(activity).application
        val viewModelFactory = VideosDetailsViewModel.Factory(vidoesObj, application)
        binding.videoDetailsViewModel = ViewModelProviders.of(
            this, viewModelFactory
        ).get(VideosDetailsViewModel::class.java)
        return binding.root
    }


}
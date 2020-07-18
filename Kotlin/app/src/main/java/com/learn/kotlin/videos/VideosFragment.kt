package com.learn.kotlin.videos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.learn.kotlin.R
import com.learn.kotlin.databinding.FragmentVideosBinding
import com.learn.kotlin.domain.VideoStoryModel

class VideosFragment : Fragment() {

    /**
     * One way to delay creation of the viewModel until an appropriate lifecycle method is to use
     * lazy. This requires that viewModel not be referenced before onActivityCreated,
     */
    private val viewModel: VideosViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "We can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(
            this,
            VideosViewModel.Factory(activity.application)
        )
            .get(VideosViewModel::class.java)
    }

    /**
     * RecyclerView Adapter for converting a list of videos to cards.
     */
    private var viewModelAdapter: VideosListAdapter? = null


    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.videoList.observe(viewLifecycleOwner, Observer<List<VideoStoryModel>> { videos ->
            videos?.apply {
                viewModelAdapter?.video = videos
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentVideosBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_videos, container, false
            )

        setHasOptionsMenu(true)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.videoViewModel = viewModel
        viewModelAdapter = VideosListAdapter(VideosListAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })

        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(
                    VideosFragmentDirections.actionVideoToDetails(it)
                )
                viewModel.displayPropertyDetailsComplete()
            }
        })

        binding.root.findViewById<RecyclerView>(R.id.rvVideos).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }
        // Observer for the network error.
        viewModel.eventNetworkError.observe(
            viewLifecycleOwner,
            Observer<Boolean> { isNetworkError ->
                if (isNetworkError) onNetworkError()
            })
        return binding.root
    }

    /**
     * Method for displaying a Toast error message for network errors.
     */
    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

}
package com.learn.kotlin.videos

import android.app.Application
import androidx.lifecycle.*
import com.learn.kotlin.database.VideosDatabase.Companion.getInstance
import com.learn.kotlin.domain.VideoStoryModel
import com.learn.kotlin.repository.VideosRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.IOException


class VideosViewModel(application: Application) : AndroidViewModel(application) {

    var status = VideoApiStatus.LOADING

    /**
     * The data source this ViewModel will fetch results from.
     */
    private val videoRepository = VideosRepository(getInstance(application))

    /**
     * A list of videos displayed on the screen.
     */
    val videoList = videoRepository.video

    /**
     * This is the job for all coroutines started by this ViewModel.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = SupervisorJob()

    /**
     * This is the main scope for all coroutines launched by MainViewModel.
     *
     * Since we pass viewModelJob, you can cancel all coroutines launched by uiScope by calling
     * viewModelJob.cancel()
     */
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * Event triggered for network error. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    /**
     * Event triggered for network error. Views should use this to get access
     * to the data.
     */
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    /**
     * Flag to display the error message. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    /**
     * Flag to display the error message. Views should use this to get access
     * to the data.
     */
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown


    private val _navigateToSelectedProperty = MutableLiveData<VideoStoryModel>()
    val navigateToSelectedProperty: LiveData<VideoStoryModel>
        get() = _navigateToSelectedProperty


    fun displayPropertyDetails(videoData: VideoStoryModel) {
        _navigateToSelectedProperty.value = videoData
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

    /**
     * init{} is called immediately when this ViewModel is created.
     */
    init {
        refreshDataFromRepository()
    }

    /**
     * Refresh data from the repository. Use a coroutine launch to run in a
     * background thread.
     */
    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                status = VideoApiStatus.LOADING
                videoRepository.refreshVideos()
                status = VideoApiStatus.DONE
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false

            } catch (networkError: IOException) {
                if (videoList.value.isNullOrEmpty()) {
                    _eventNetworkError.value = true
                    status = VideoApiStatus.ERROR
                }

            }
        }
    }


    /**
     * Resets the network error flag.
     */
    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }


    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * Factory for constructing VideoDetailsViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(VideosViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return VideosViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}

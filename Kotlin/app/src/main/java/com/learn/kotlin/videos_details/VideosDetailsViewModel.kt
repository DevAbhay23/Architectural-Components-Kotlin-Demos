package com.learn.kotlin.videos_details

import android.app.Application
import androidx.lifecycle.*
import com.learn.kotlin.domain.VideoStoryModel


class VideosDetailsViewModel(videoObj: VideoStoryModel, application: Application) :
    AndroidViewModel(application) {


    private val _selectedProperty = MutableLiveData<VideoStoryModel>()
    val selectedProperty: LiveData<VideoStoryModel>
        get() = _selectedProperty

    init {
        _selectedProperty.value = videoObj
    }

    /**
     * Factory for constructing VideoDetailsViewModel with parameter
     */
    class Factory(private val videoObj: VideoStoryModel, val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(VideosDetailsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return VideosDetailsViewModel(videoObj, app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
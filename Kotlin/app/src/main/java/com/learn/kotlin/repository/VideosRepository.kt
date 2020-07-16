package com.learn.kotlin.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.learn.kotlin.database.VideosDatabase
import com.learn.kotlin.database.asDomainModel
import com.learn.kotlin.domain.VideoStoryModel
import com.learn.kotlin.network.VideoApi
import com.learn.kotlin.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideosRepository (private val database: VideosDatabase) {
    
    val video: LiveData<List<VideoStoryModel>> = Transformations.map(database.videosDatabaseDao.getAllRecords()) {
        it.asDomainModel()
    }


    /**
     * Refresh the video stored in the offline cache.
     *
     * This function uses the IO dispatcher to ensure the database insert database operation
     * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
     * function is now safe to call from any thread including the Main thread.
     *
     */
    suspend fun refreshVideos() {
        withContext(Dispatchers.IO) {
            val videosList = VideoApi.retrofitService.getVideosStoriesAsync().await()
            database.videosDatabaseDao.insertAll(videosList.asDatabaseModel())
        }
    }
}
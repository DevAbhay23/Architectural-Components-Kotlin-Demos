package com.learn.kotlin.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.learn.kotlin.database.VideosDatabase.Companion.getInstance
import com.learn.kotlin.repository.VideosRepository
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = " com.learn.kotlin.videos.work.RefreshDataWorker"    // uniqueWorkName
    }

    // perform the task inside doWork //
    override suspend fun doWork(): Result {
        val database = getInstance(applicationContext)
        val repository = VideosRepository(database)
        try {
            repository.refreshVideos()
            Log.d("WM", "Work request for sync is run")
        } catch (e: HttpException) {
            return Result.retry()
        }
        return Result.success()    // To indicate that the work completed successfully.
    }
}
package com.learn.kotlin.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface VideosStoryDao {

    @Insert
    fun insertRecord(videos: DatabaseVideos)

    @Update
    fun updateRecord(videos: DatabaseVideos)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(videosList: List<DatabaseVideos>)

    @Query("SELECT * from videos WHERE recordId = :key")
    fun getRecord(key: Long): DatabaseVideos?

    @Query("DELETE FROM videos WHERE recordId = :key")
    fun deleteRecord(key: Long)

    @Query("SELECT * FROM videos ORDER BY updated DESC")
    fun getAllRecords(): LiveData<List<DatabaseVideos>>

    @Query("DELETE FROM videos")
    fun deleteAllRecords()
}
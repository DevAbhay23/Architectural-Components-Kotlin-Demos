package com.learn.kotlin.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.learn.kotlin.domain.VideoStoryModel

/**
 * DatabaseVideos represents a videos entity in the database.
 */
@Entity(tableName = "videos", indices = [Index(value = ["title"], unique = true)])
        data class DatabaseVideos constructor(
    @PrimaryKey(autoGenerate = true)
    var recordId: Long = 0L,
    val title: String,
    val description: String,
    val updated: String,
    val thumbnail: String
)


    /**
     * Map DatabaseVideos to domain entities
     */
    fun List<DatabaseVideos>.asDomainModel(): List<VideoStoryModel> {
        return map {
            VideoStoryModel(
                recordId = it.recordId,
                thumbnail = it.thumbnail,
                title = it.title,
                description = it.description,
                updated = it.updated
            )
        }
    }

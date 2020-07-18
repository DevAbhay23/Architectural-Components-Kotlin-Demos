package com.learn.kotlin.network

import com.learn.kotlin.database.DatabaseVideos
import com.squareup.moshi.JsonClass

/**
 * VideoHolder holds a list of videos hits.
 *
 * This is to parse first level of our network result which looks like
 *
 * {
 *   "videos": []
 * }
 */
@JsonClass(generateAdapter = true)
data class NetworkVideosContainer(val videos: List<NetworkVideosHits>)

/**
 * Hits represent a videos that can be read.
 */
@JsonClass(generateAdapter = true)
data class NetworkVideosHits(
    val title: String,
    val updated: String,
    val description: String,
    val thumbnail: String?
)

/**
 * Convert Network results to database objects
 */
fun NetworkVideosContainer.asDatabaseModel(): List<DatabaseVideos> {
    return videos.map {
        DatabaseVideos(
            recordId = 0L,
            title = it.title,
            updated = it.updated,
            description = it.description,
            thumbnail = it.thumbnail.toString()
        )
    }
}


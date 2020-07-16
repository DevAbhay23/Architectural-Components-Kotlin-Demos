package com.learn.kotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabaseVideos::class], version = 1, exportSchema = false)
abstract class VideosDatabase : RoomDatabase() {

    abstract val videosDatabaseDao: VideosStoryDao

    companion object {
        @Volatile
        private var INSTANCE: VideosDatabase? = null
        fun getInstance(context: Context): VideosDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        VideosDatabase::class.java,
                        "videos_database"
                    )
                        .fallbackToDestructiveMigration()     //  destroy and rebuild the database, which means that the data is lost. , no migration starategy
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
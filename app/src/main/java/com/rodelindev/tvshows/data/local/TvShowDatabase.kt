package com.rodelindev.tvshows.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rodelindev.tvshows.data.local.converter.SeasonConverter
import com.rodelindev.tvshows.data.local.dao.RemoteKeysDao
import com.rodelindev.tvshows.data.local.dao.TvShowDao
import com.rodelindev.tvshows.data.local.entities.RemoteKeysEntity
import com.rodelindev.tvshows.data.local.entities.TvShowEntity

@Database(
    entities = [
        TvShowEntity::class,
        RemoteKeysEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(SeasonConverter::class)
abstract class TvShowDatabase: RoomDatabase() {

    abstract fun tvShowDao(): TvShowDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        const val DB_NAME = "tvShows_db"
    }
}
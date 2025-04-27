package com.rodelindev.tvshows.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rodelindev.tvshows.data.local.entities.TvShowEntity

@Dao
interface TvShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(tvShow: List<TvShowEntity>)

    @Query("DELETE FROM tv_shows WHERE id = :id")
    suspend fun deleteTvShowById(id: Int)

    @Query("SELECT * FROM tv_shows")
    suspend fun getFavoritesTvShows(): List<TvShowEntity>
}
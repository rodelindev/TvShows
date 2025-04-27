package com.rodelindev.tvshows.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rodelindev.tvshows.data.local.entities.RemoteKeysEntity

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeysEntity>)

    @Query("SELECT * FROM remote_keys WHERE tvShowId = :tvShowId AND category = :category")
    suspend fun remoteKeysTvShowId(tvShowId: String, category: String): RemoteKeysEntity

    @Query("DELETE FROM remote_keys WHERE category = :category")
    suspend fun clearRemoteKeys(category: String)
}
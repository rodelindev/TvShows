package com.rodelindev.tvshows.domain.repository

import androidx.paging.PagingData
import com.rodelindev.tvshows.data.local.entities.TvShowEntity
import com.rodelindev.tvshows.domain.model.RemoteMovie
import com.rodelindev.tvshows.domain.model.TvShow
import com.rodelindev.tvshows.domain.model.TvShowFilter
import kotlinx.coroutines.flow.Flow

interface TvShowRepository {
    fun getTvShows(tvShowFilter: TvShowFilter): Flow<PagingData<TvShow>>
    suspend fun getDetailTvShowById(tvShowId: Int): Result<TvShow>
    suspend fun toggleFavorite(tvShow: TvShow)
    suspend fun getFavoritesTvShows(): List<TvShow>
}
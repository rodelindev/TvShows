package com.rodelindev.tvshows.data.repository


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rodelindev.tvshows.data.local.dao.TvShowDao
import com.rodelindev.tvshows.data.local.entities.TvShowEntity
import com.rodelindev.tvshows.data.mapper.toDomain
import com.rodelindev.tvshows.data.mapper.toTvShowEntity
import com.rodelindev.tvshows.data.remote.TvShowRemoteMediator
import com.rodelindev.tvshows.data.remote.service.TvShowApi
import com.rodelindev.tvshows.data.remote.util.resultOf
import com.rodelindev.tvshows.data.util.DataConstants.NETWORK_PAGE_SIZE
import com.rodelindev.tvshows.domain.model.TvShow
import com.rodelindev.tvshows.domain.model.TvShowFilter
import com.rodelindev.tvshows.domain.repository.TvShowRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvShowRepositoryImpl @Inject constructor(
    private val tvShowDao: TvShowDao,
    private val tvShowApi: TvShowApi,
) : TvShowRepository {

    override fun getTvShows(tvShowFilter: TvShowFilter): Flow<PagingData<TvShow>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                prefetchDistance = 2
            ),
            pagingSourceFactory = {
                TvShowRemoteMediator(
                    tvShowApi = tvShowApi,
                    tvShowFilter = tvShowFilter
                )
            }
        ).flow
    }

    override suspend fun getDetailTvShowById(tvShowId: Int): Result<TvShow> = resultOf {
        val tvShow = tvShowApi.getDetailTvShowById(tvShowId).toDomain()
        tvShow.copy(isFavorite = tvShowDao.getFavoritesTvShows().contains(tvShow.toTvShowEntity()))
    }

    override suspend fun toggleFavorite(tvShow: TvShow) {
        if (tvShowDao.getFavoritesTvShows().contains(tvShow.toTvShowEntity())) {
            tvShowDao.deleteTvShowById(tvShow.id)
        } else {
            tvShowDao.save(listOf(tvShow.copy(isFavorite = !tvShow.isFavorite).toTvShowEntity()))
        }
    }

    override suspend fun getFavoritesTvShows(): List<TvShowEntity> {
        return tvShowDao.getFavoritesTvShows()
    }
}
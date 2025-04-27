package com.rodelindev.tvshows.domain.usecase

import androidx.paging.PagingData
import com.rodelindev.tvshows.domain.model.TvShow
import com.rodelindev.tvshows.domain.model.TvShowFilter
import com.rodelindev.tvshows.domain.repository.TvShowRepository
import kotlinx.coroutines.flow.Flow


class GetTvShowUseCase(
    private val repository: TvShowRepository,
) {
    operator fun invoke(tvShowFilter: TvShowFilter): Flow<PagingData<TvShow>> {
        return repository.getTvShows(tvShowFilter)
    }
}

package com.rodelindev.tvshows.domain.usecase

import com.rodelindev.tvshows.domain.model.TvShow
import com.rodelindev.tvshows.domain.repository.TvShowRepository

class GetDetailTvShowUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int): Result<TvShow> {
        return tvShowRepository.getDetailTvShowById(tvShowId)
    }
}


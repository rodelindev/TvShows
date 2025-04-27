package com.rodelindev.tvshows.domain.usecase

import com.rodelindev.tvshows.data.local.entities.TvShowEntity
import com.rodelindev.tvshows.domain.model.TvShow
import com.rodelindev.tvshows.domain.repository.TvShowRepository

class GetFavoriteTvShowsUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(): List<TvShowEntity> = tvShowRepository.getFavoritesTvShows()
}
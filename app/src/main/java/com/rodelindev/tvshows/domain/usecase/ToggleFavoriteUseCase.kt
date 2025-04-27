package com.rodelindev.tvshows.domain.usecase

import com.rodelindev.tvshows.domain.model.TvShow
import com.rodelindev.tvshows.domain.repository.TvShowRepository

class ToggleFavoriteUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShow: TvShow){
        tvShowRepository.toggleFavorite(tvShow)
    }
}
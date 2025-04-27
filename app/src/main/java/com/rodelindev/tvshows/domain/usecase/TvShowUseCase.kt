package com.rodelindev.tvshows.domain.usecase

data class TvShowUseCase(
    val getTvShowUseCase: GetTvShowUseCase,
    val getDetailTvShowUseCase: GetDetailTvShowUseCase,
    val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    val getFavoriteTvShowsUseCase: GetFavoriteTvShowsUseCase
)

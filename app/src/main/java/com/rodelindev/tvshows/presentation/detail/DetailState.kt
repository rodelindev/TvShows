package com.rodelindev.tvshows.presentation.detail

import com.rodelindev.tvshows.domain.model.TvShow

data class DetailState(
    val tvShow: TvShow? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

package com.rodelindev.tvshows.presentation.detail

import com.rodelindev.tvshows.domain.model.TvShow

sealed interface DetailEvent {
    data object SetAsFavorite : DetailEvent
    //data object DeleteFromFavorites : DetailEvent
}
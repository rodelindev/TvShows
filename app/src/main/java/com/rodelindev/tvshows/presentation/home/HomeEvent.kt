package com.rodelindev.tvshows.presentation.home

import com.rodelindev.tvshows.domain.model.TvShowFilter

sealed interface HomeEvent {
    data class ChangeFilter(val tvShowFilter: TvShowFilter) : HomeEvent
}
package com.rodelindev.tvshows.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rodelindev.tvshows.domain.model.TvShow
import com.rodelindev.tvshows.domain.model.TvShowFilter
import com.rodelindev.tvshows.domain.usecase.TvShowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tvShowUseCase: TvShowUseCase,
) : ViewModel() {

    private val _tvShowsState: MutableStateFlow<PagingData<TvShow>> = MutableStateFlow(value = PagingData.empty())
    val tvShowsState: MutableStateFlow<PagingData<TvShow>> get() = _tvShowsState

    fun onEvent(event: HomeEvent) {
        viewModelScope.launch {
            when (event) {
                is HomeEvent.ChangeFilter -> {
                    searchTvShows(event.tvShowFilter)
                }
            }
        }
    }

    private suspend fun searchTvShows(tvShowFilter: TvShowFilter) {
        tvShowUseCase.getTvShowUseCase(tvShowFilter = tvShowFilter)
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect {
                _tvShowsState.value = it
                println(it)
            }
    }

    /*val tvShowsState = _tvShowsState.asStateFlow()*/

    /*private val _tvShowsState: MutableStateFlow<PagingData<TvShow>> = MutableStateFlow(value = PagingData.empty())
    val tvShowsState = _tvShowsState.asStateFlow()*/

    /*fun searchTvShows(tvShowFilter: TvShowFilter) {
        _tvShowsState.value = tvShowUseCase.getTvShowUseCase(tvShowFilter = tvShowFilter)
            .flowOn(dispatcher)
            .cachedIn(viewModelScope)
    }*/

    /*init {
        viewModelScope.launch {
            searchTvShows(TvShowFilter.TOP_RATED)
        }
    }*/
}
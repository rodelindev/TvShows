package com.rodelindev.tvshows.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.rodelindev.tvshows.di.IoDispatcher
import com.rodelindev.tvshows.domain.usecase.TvShowUseCase
import com.rodelindev.tvshows.navigation.NavigationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val useCases: TvShowUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state.asStateFlow()

    init {
        val tvShowId = savedStateHandle.toRoute<NavigationRoute.Detail>()
        getTvShowById(id = tvShowId.id)
    }

    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.SetAsFavorite -> {
                setFavoriteTvShow()
            }
        }
    }

    private fun getTvShowById(id: Int) {
        _state.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch(dispatcher) {
            useCases.getDetailTvShowUseCase(id).onSuccess { tvShowData ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        tvShow = tvShowData
                    )
                }
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = error.message
                    )
                }
            }
        }
    }

    private fun setFavoriteTvShow() {
        _state.value.tvShow.let {tvShow ->
            viewModelScope.launch(dispatcher) {
                useCases.toggleFavoriteUseCase(tvShow!!)
            }
        }
    }
}
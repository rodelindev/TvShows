package com.rodelindev.tvshows.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodelindev.tvshows.di.IoDispatcher
import com.rodelindev.tvshows.domain.model.TvShow
import com.rodelindev.tvshows.domain.usecase.TvShowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCases: TvShowUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _favoritesTvShows = MutableStateFlow<List<TvShow>>(emptyList())
    val favoritesTvShows = _favoritesTvShows.asStateFlow()

    fun getFavorites() {
        viewModelScope.launch(dispatcher) {
            _favoritesTvShows.value = useCases.getFavoriteTvShowsUseCase()
        }
    }
}
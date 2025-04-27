package com.rodelindev.tvshows.di

import com.rodelindev.tvshows.data.local.dao.TvShowDao
import com.rodelindev.tvshows.data.remote.service.TvShowApi
import com.rodelindev.tvshows.data.repository.TvShowRepositoryImpl
import com.rodelindev.tvshows.domain.repository.TvShowRepository
import com.rodelindev.tvshows.domain.usecase.GetDetailTvShowUseCase
import com.rodelindev.tvshows.domain.usecase.GetFavoriteTvShowsUseCase
import com.rodelindev.tvshows.domain.usecase.GetTvShowUseCase
import com.rodelindev.tvshows.domain.usecase.ToggleFavoriteUseCase
import com.rodelindev.tvshows.domain.usecase.TvShowUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeRepositoryModule {

    @Provides
    @Singleton
    fun providesTvRepository(
        tvShowApi: TvShowApi,
        tvShowDao: TvShowDao,
    ): TvShowRepository {
        return TvShowRepositoryImpl(
            tvShowApi = tvShowApi,
            tvShowDao = tvShowDao
        )
    }
    
    @Provides
    @Singleton
    fun providesTvShowUseCase(tvShowRepository: TvShowRepository): TvShowUseCase {
        return TvShowUseCase(
            getTvShowUseCase = GetTvShowUseCase(tvShowRepository),
            getDetailTvShowUseCase = GetDetailTvShowUseCase(tvShowRepository),
            toggleFavoriteUseCase = ToggleFavoriteUseCase(tvShowRepository),
            getFavoriteTvShowsUseCase = GetFavoriteTvShowsUseCase(tvShowRepository)
        )
    }
}
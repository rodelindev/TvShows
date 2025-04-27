package com.rodelindev.tvshows.data.remote.service

import com.rodelindev.tvshows.data.remote.response.TvShowDTO
import com.rodelindev.tvshows.data.remote.response.TvShowResponseDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowApi {

    @GET("tv/popular")
    suspend fun getPopularTvShows(
        @Query("page") page: Int
    ): TvShowResponseDTO

    @GET("tv/top_rated")
    suspend fun getTopRatedTvShows(
        @Query("page") page: Int
    ): TvShowResponseDTO

    @GET("tv/airing_today")
    suspend fun getAiringTodayTvShows(
        @Query("page") page: Int
    ): TvShowResponseDTO

    @GET("tv/on_the_air")
    suspend fun getOnTheAirTvShows(
        @Query("page") page: Int
    ): TvShowResponseDTO

    @GET("tv/{series_id}")
    suspend fun getDetailTvShowById(
        @Path("series_id") seriesId: Int
    ): TvShowDTO

}
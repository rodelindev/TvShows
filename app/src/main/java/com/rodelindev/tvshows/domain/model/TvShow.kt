package com.rodelindev.tvshows.domain.model

data class TvShow(
    val backdropPath: String,
    val firstAirDate: String,
    val id: Int,
    val name: String,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String? = "",
    val voteAverage: Double,
    val voteCount: Int,
    var isFavorite: Boolean = false,
    var category: String = "",
    val seasons: List<Season>
)
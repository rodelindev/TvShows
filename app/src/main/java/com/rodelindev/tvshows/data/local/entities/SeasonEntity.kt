package com.rodelindev.tvshows.data.local.entities

data class SeasonEntity(
    val airDate: String = "",
    val episodeCount: Int,
    val id: Int,
    val name: String,
    val overview: String,
    val posterPath: String? = "",
    val seasonNumber: Int,
    val voteAverage: Double
)
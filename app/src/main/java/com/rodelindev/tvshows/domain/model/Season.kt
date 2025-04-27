package com.rodelindev.tvshows.domain.model

data class Season(
    val id: Int,
    val airDate: String? = null,
    val episodeCount: Int,
    val name: String,
    val overview: String,
    val posterPath: String? = "",
    val seasonNumber: Int,
    val voteAverage: Double
)


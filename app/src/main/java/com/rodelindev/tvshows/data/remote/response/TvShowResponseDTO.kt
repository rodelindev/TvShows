package com.rodelindev.tvshows.data.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowResponseDTO(
    val page: Int,
    val results: List<TvShowDTO>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int,
)
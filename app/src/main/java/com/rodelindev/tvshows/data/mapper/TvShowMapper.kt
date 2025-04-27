package com.rodelindev.tvshows.data.mapper

import com.rodelindev.tvshows.data.local.entities.SeasonEntity
import com.rodelindev.tvshows.data.local.entities.TvShowEntity
import com.rodelindev.tvshows.data.remote.response.SeasonDTO
import com.rodelindev.tvshows.data.remote.response.TvShowDTO
import com.rodelindev.tvshows.domain.model.Season
import com.rodelindev.tvshows.domain.model.TvShow

/*TO Model mapper to Domain model*/

fun SeasonDTO.toDomain(): Season {
    return Season(
        airDate = airDate,
        episodeCount = episodeCount,
        id = id,
        name = name,
        overview = overview,
        posterPath = posterPath,
        seasonNumber = seasonNumber,
        voteAverage = voteAverage
    )
}

fun TvShowDTO.toDomain(): TvShow {
    return TvShow(
        backdropPath = backdropPath ?: "",
        firstAirDate = firstAirDate,
        id = id,
        name = name,
        originalLanguage = originalLanguage,
        originalName = originalName,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath ?: "",
        voteAverage = voteAverage,
        voteCount = voteCount,
        isFavorite = false,
        seasons = seasons?.map { it.toDomain() }.orEmpty()
    )
}

fun List<TvShowDTO>.toDomainList(): List<TvShow> {
    return this.map {
        it.toDomain()
    }
}

fun TvShow.toTvShowEntity(): TvShowEntity {
    return TvShowEntity(
        backdropPath = backdropPath,
        firstAirDate = firstAirDate,
        id = id,
        name = name,
        originalLanguage = originalLanguage,
        originalName = originalName,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath ?: "",
        voteAverage = voteAverage,
        voteCount = voteCount,
        isFavorite = false,
        seasons = seasons.map { it.toSeasonEntity() }
    )
}

fun Season.toSeasonEntity(): SeasonEntity {
    return SeasonEntity(
        airDate = airDate.toString(),
        episodeCount = episodeCount,
        id = id,
        name = name,
        overview = overview,
        posterPath = posterPath,
        seasonNumber = seasonNumber,
        voteAverage = voteAverage
    )
}

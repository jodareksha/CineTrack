package com.cinetrack.app.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetailDto(
    val id: Int,
    val title: String,
    val overview: String,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "release_date") val releaseDate: String?,
    val runtime: Int?,
    val genres: List<GenreDto>
)

@JsonClass(generateAdapter = true)
data class GenreDto(
    val id: Int,
    val name: String
)

@JsonClass(generateAdapter = true)
data class CreditsDto(
    val cast: List<CastMemberDto>
)

@JsonClass(generateAdapter = true)
data class CastMemberDto(
    val id: Int,
    val name: String,
    val character: String
)
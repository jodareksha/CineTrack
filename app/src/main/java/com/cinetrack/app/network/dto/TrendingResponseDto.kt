package com.cinetrack.app.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * DTO = Data Transfer Object. Ini adalah representasi MENTAH dari JSON response TMDB,
 * belum di-mapping ke model domain kita. Pemisahan DTO vs domain model ini baru
 * benar-benar terasa manfaatnya di Phase 1 (Clean Architecture / data layer),
 * tapi kita mulai biasakan dari sekarang.
 */
@JsonClass(generateAdapter = true)
data class TrendingResponseDto(
    val page: Int,
    val results: List<MovieDto>,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "total_results") val totalResults: Int
)

@JsonClass(generateAdapter = true)
data class MovieDto(
    val id: Int,
    val title: String,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "release_date") val releaseDate: String?,
    val overview: String
)

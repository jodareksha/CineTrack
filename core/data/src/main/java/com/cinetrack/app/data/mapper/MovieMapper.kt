package com.cinetrack.app.data.mapper

import com.cinetrack.app.domain.model.Movie
import com.cinetrack.app.domain.model.MovieDetail
import com.cinetrack.app.core.takeUpTo
import com.cinetrack.app.core.toRatingLabel
import com.cinetrack.app.core.toReleaseYear
import com.cinetrack.app.network.dto.CreditsDto
import com.cinetrack.app.network.dto.MovieDetailDto
import com.cinetrack.app.network.dto.MovieDto

private const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w185"

fun MovieDto.toDomain(): Movie {
    return Movie(
        id = id,
        title= title,
        overview= overview,
        posterUrl=posterPath?.let{ "$POSTER_BASE_URL$it" },
        ratingLabel= voteAverage.toRatingLabel(),
        releaseYear = (releaseDate ?: "").toReleaseYear(),
    )
}

fun List<MovieDto>.toDomainList(): List<Movie> = map { it.toDomain() }

fun toMovieDetailDomain(detail: MovieDetailDto, credits: CreditsDto): MovieDetail {
    return MovieDetail(
        id = detail.id,
        title = detail.title,
        overview = detail.overview,
        posterUrl = detail.posterPath?.let { POSTER_BASE_URL + it },
        ratingLabel= detail.voteAverage.toRatingLabel(),
        releaseYear = (detail.releaseDate ?: "").toReleaseYear(),
        runtimeLabel = detail.runtime?.let {"$it min"} ?: "UNKNOWN",
        genres = detail.genres.map {it.name },
        castNames = credits.cast.map { it.name }.takeUpTo(5)
    )
}
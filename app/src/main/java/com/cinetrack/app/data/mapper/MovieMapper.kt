package com.cinetrack.app.data.mapper

import com.cinetrack.app.domain.model.Movie
import com.cinetrack.app.kata.toRatingLabel
import com.cinetrack.app.kata.toReleaseYear
import com.cinetrack.app.network.dto.MovieDto

private const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"

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

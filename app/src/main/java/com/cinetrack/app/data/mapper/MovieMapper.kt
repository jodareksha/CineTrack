package com.cinetrack.app.data.mapper

import com.cinetrack.app.domain.model.Movie
import com.cinetrack.app.domain.model.MovieDetail
import com.cinetrack.app.kata.takeUpTo
import com.cinetrack.app.kata.toRatingLabel
import com.cinetrack.app.kata.toReleaseYear
import com.cinetrack.app.network.dto.CreditsDto
import com.cinetrack.app.network.dto.MovieDetailDto
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
/**
 * TODO (fitur detail): Gabungkan MovieDetailDto + CreditsDto jadi 1 MovieDetail.
 *
 * Mapping tiap field:
 * - id, title, overview -> ambil langsung dari `detail`
 * - posterUrl -> POSTER_BASE_URL + detail.posterPath (null-safe)
 * - ratingLabel -> detail.voteAverage.toRatingLabel()
 * - releaseYear -> (detail.releaseDate ?: "").toReleaseYear()
 * - runtimeLabel -> detail.runtime itu Int? (menit). Format "142 min" kalau ada,
 *   "Unknown" kalau null (pakai ?.let + elvis)
 * - genres -> detail.genres.map { it.name }
 * - castNames -> credits.cast.map { it.name }, batasi 5 pakai .takeUpTo(5)
 */
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
package com.cinetrack.app.domain.repository

import com.cinetrack.app.core.Result
import com.cinetrack.app.domain.model.Movie
import com.cinetrack.app.domain.model.MovieDetail


interface MovieRepository {
    suspend fun getTrendingMovies(): Result<List<Movie>>
    suspend fun searchMovies(query: String): Result<List<Movie>>
    suspend fun getMovieDetail(movieId: Int): Result<MovieDetail>

}

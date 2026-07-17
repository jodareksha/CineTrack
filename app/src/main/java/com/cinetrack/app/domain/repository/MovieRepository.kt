package com.cinetrack.app.domain.repository

import com.cinetrack.app.core.Result
import com.cinetrack.app.domain.model.Movie
import retrofit2.http.Query


interface MovieRepository {
    suspend fun getTrendingMovies(): Result<List<Movie>>
    suspend fun searchMovies(query: String): Result<List<Movie>>
}

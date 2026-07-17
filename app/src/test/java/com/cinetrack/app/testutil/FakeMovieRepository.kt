package com.cinetrack.app.testutil

import com.cinetrack.app.core.Result
import com.cinetrack.app.domain.model.Movie
import com.cinetrack.app.domain.model.MovieDetail
import com.cinetrack.app.domain.repository.MovieRepository

class FakeMovieRepository(
    private val trendingResult: Result<List<Movie>> = Result.Success(emptyList()),
    private val searchResult: Result<List<Movie>> = Result.Success(emptyList()),
    private val detailResult: Result<MovieDetail> = Result.Error("not stubbed")
) : MovieRepository {
    override suspend fun getTrendingMovies(): Result<List<Movie>> = trendingResult
    override suspend fun searchMovies(query: String): Result<List<Movie>> = searchResult
    override suspend fun getMovieDetail(movieId: Int): Result<MovieDetail> = detailResult
}
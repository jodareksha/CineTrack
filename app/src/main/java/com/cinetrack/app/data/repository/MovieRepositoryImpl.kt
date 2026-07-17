package com.cinetrack.app.data.repository

import com.cinetrack.app.core.Result
import com.cinetrack.app.core.fetchParallel
import com.cinetrack.app.core.map
import com.cinetrack.app.core.safeApiCall
import com.cinetrack.app.data.mapper.toDomainList
import com.cinetrack.app.data.mapper.toMovieDetailDomain
import com.cinetrack.app.domain.model.Movie
import com.cinetrack.app.domain.model.MovieDetail
import com.cinetrack.app.domain.repository.MovieRepository
import com.cinetrack.app.network.TmdbApi
import com.cinetrack.app.network.dto.TrendingResponseDto
import javax.inject.Inject


class MovieRepositoryImpl @Inject constructor(
    private val api: TmdbApi
) : MovieRepository {

/*cara biasanya panggil api*/
//        override suspend fun getTrendingMovies(): Result<List<Movie>> {
//        return safeApiCall { api.getTrendingMovies() }
//            .map { response ->
//                response.results.toDomainList()
//            }
//    }
/*cara singkat panggil api idiomatik single A-B*/
    override suspend fun getTrendingMovies(): Result<List<Movie>> = safeApiCall { api.getTrendingMovies() }.map { it.results.toDomainList() }
    override suspend fun searchMovies(query: String): Result<List<Movie>> = safeApiCall { api.searchMovies(query) }.map { it.results.toDomainList() }
    override suspend fun getMovieDetail(movieId: Int): Result<MovieDetail> {
        return safeApiCall {
          fetchParallel(
                fetchA = { api.getMovieDetail(movieId) },
                fetchB = { api.getMovieCredits(movieId) },
                combine = { detail, credits -> toMovieDetailDomain(detail, credits) }
            )
        }
    }
}
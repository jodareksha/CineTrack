package com.cinetrack.app.domain.repository

import com.cinetrack.app.core.Result
import com.cinetrack.app.domain.model.Movie

/**
 * Interface ini hidup di DOMAIN layer, tapi implementasinya (MovieRepositoryImpl)
 * hidup di DATA layer. Ini "Dependency Inversion Principle" (huruf D di SOLID):
 * ViewModel nanti akan bergantung ke interface ini, BUKAN ke implementasi konkretnya
 * atau ke Retrofit/TmdbApi langsung. Manfaatnya kelihatan jelas waktu testing:
 * kamu bisa bikin FakeMovieRepository yang implement interface ini tanpa perlu
 * network sama sekali (lihat TrendingViewModelTest.kt nanti).
 */
interface MovieRepository {
    suspend fun getTrendingMovies(): Result<List<Movie>>
}

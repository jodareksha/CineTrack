package com.cinetrack.app.network

import com.cinetrack.app.network.dto.TrendingResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit tahu cara memanggil endpoint ini karena kita pakai `suspend fun`.
 * Retrofit versi modern (2.6+) punya dukungan coroutine bawaan — TIDAK perlu
 * CallAdapter tambahan seperti dulu waktu pakai RxJava.
 */
interface TmdbApi {

    @GET("trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("language") language: String = "en-US"
    ): TrendingResponseDto
}

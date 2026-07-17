package com.cinetrack.app.network

import com.cinetrack.app.network.dto.TrendingResponseDto
import retrofit2.http.GET
import retrofit2.http.Query


interface TmdbApi {

    @GET("trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("language") language: String = "en-US"
    ): TrendingResponseDto
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("language") language: String = "en-US"
    ): TrendingResponseDto
}


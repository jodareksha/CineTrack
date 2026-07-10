package com.cinetrack.app.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Phase 0: ini "manual DI" — cuma object singleton biasa.
 * Di Phase 1 kita ganti dengan Hilt (@Module, @Provides, @Singleton).
 *
 * TUJUAN PEDAGOGIS: sebelum pakai Hilt, kamu harus paham betul APA yang
 * sebenarnya di-generate oleh Hilt di belakang layar. Kalau langsung loncat
 * ke Hilt tanpa paham ini, kamu cuma hafal anotasi tanpa ngerti kenapa.
 */
object NetworkModule {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private val authInterceptor = okhttp3.Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${com.cinetrack.app.BuildConfig.TMDB_API_KEY}")
            .addHeader("accept", "application/json")
            .build()
        chain.proceed(request)
    }
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val tmdbApi: TmdbApi by lazy {
        retrofit.create(TmdbApi::class.java)
    }
}

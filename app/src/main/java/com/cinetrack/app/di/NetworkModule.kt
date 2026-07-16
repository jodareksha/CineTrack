package com.cinetrack.app.di

import com.cinetrack.app.BuildConfig
import com.cinetrack.app.network.TmdbApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://api.themoviedb.org/3/"

/**
 * Ini pengganti object NetworkModule manual dari Phase 0. Bandingkan dengan versi lama
 * kamu (sudah dihapus) — perhatikan APA yang sebenarnya digantikan oleh Hilt:
 *
 * - Dulu: `object NetworkModule { val tmdbApi by lazy { ... } }` — kamu yang atur
 *   urutan pembuatan (moshi -> okhttp -> retrofit -> api) secara manual.
 * - Sekarang: tiap @Provides function cuma butuh declare APA yang dia butuhkan sebagai
 *   parameter (misal provideRetrofit butuh OkHttpClient dan Moshi), dan Hilt yang
 *   otomatis urutkan pemanggilannya berdasarkan dependency graph. @Singleton memastikan
 *   cuma dibuat SEKALI selama app hidup, sama seperti `by lazy` dulu.
 *
 * @InstallIn(SingletonComponent::class) artinya semua yang di-provide di sini hidup
 * selama Application hidup (bukan cuma selama 1 Activity).
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideAuthInterceptor(): Interceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${BuildConfig.TMDB_API_KEY}")
            .addHeader("accept", "application/json")
            .build()
        chain.proceed(request)
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    @Singleton
    fun provideTmdbApi(retrofit: Retrofit): TmdbApi = retrofit.create(TmdbApi::class.java)
}

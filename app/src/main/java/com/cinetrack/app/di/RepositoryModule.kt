package com.cinetrack.app.di

import com.cinetrack.app.data.repository.MovieRepositoryImpl
import com.cinetrack.app.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @Binds itu cara Hilt yang lebih ringkas dibanding @Provides KHUSUS untuk kasus
 * "kalau ada yang minta interface X, kasih instance implementasi Y" — tidak perlu
 * nulis constructor call manual seperti @Provides, karena MovieRepositoryImpl sudah
 * punya @Inject constructor (lihat file-nya) yang Hilt bisa panggil otomatis.
 *
 * Module harus `abstract class` (bukan object) kalau isinya @Binds function.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMovieRepository(
        impl: MovieRepositoryImpl
    ): MovieRepository
}

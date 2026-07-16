package com.cinetrack.app.presentation.trending

import com.cinetrack.app.domain.model.Movie

/**
 * Perhatikan: ini sealed interface TERPISAH dari Result<T> di core/Result.kt, meskipun
 * bentuknya mirip (Loading/Success/Error). Ini KEPUTUSAN SENGAJA, bukan duplikasi
 * yang salah:
 *
 * - Result<T> itu generic, dipakai di data/domain layer untuk BANYAK jenis operasi.
 * - TrendingUiState itu SPESIFIK untuk 1 layar ini, sudah punya bentuk final yang
 *   siap ditampilkan (List<Movie>, bukan generic T), dan nanti seiring layar makin
 *   kompleks (misal ada state "refreshing" sambil tetap nampilin data lama), UI state
 *   ini bisa berkembang sendiri tanpa harus ubah Result<T> yang dipakai di tempat lain.
 *
 * Ini pola yang akan kamu lihat terus di project nyata: domain/data pakai tipe generik,
 * presentation layer punya tipe UI state yang lebih spesifik per layar.
 */
sealed interface TrendingUiState {
    data object Loading : TrendingUiState
    data class Success(val movies: List<Movie>) : TrendingUiState
    data class Error(val message: String) : TrendingUiState
}

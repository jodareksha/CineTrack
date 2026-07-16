package com.cinetrack.app.core

/**
 * Ini "versi produksi" dari Result yang kamu bangun sendiri di kata/SealedClasses.kt.
 * Perhatikan 2 perbedaan dari versi kata:
 *
 * 1. `map` di sini adalah EXTENSION FUNCTION (`result.map { }`), bukan static function
 *    (`ResultKata.map(result) { }`) seperti di kata. Ini lebih idiomatik dan lebih enak
 *    dipakai dalam chain.
 *
 * 2. `safeApiCall` di sini sudah FIX bug yang kita diskusikan waktu kamu ngerjain kata #3:
 *    CancellationException sekarang di-rethrow, tidak ikut ketangkep jadi Result.Error.
 *    Ini penting supaya structured concurrency tetap bekerja dengan benar di real app.
 *
 * Kata Phase 0 kamu TETAP DIBIARKAN seperti apa adanya di folder kata/ — itu jadi arsip
 * bukti belajar kamu, tidak perlu dihapus atau disamakan dengan versi ini.
 */
sealed interface Result<out T> {
    object Loading : Result<Nothing>
    data class Success<T>(val data: T) : Result<T>
    data class Error(val message: String, val cause: Throwable? = null) : Result<Nothing>
}

fun <T, R> Result<T>.map(transform: (T) -> R): Result<R> = when (this) {
    is Result.Success -> Result.Success(transform(data))
    is Result.Loading -> Result.Loading
    is Result.Error -> Result.Error(message, cause)
}

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
    return try {
        Result.Success(apiCall())
    } catch (e: Throwable) {
        if (e is kotlinx.coroutines.CancellationException) throw e
        Result.Error(message = e.message ?: "Unknown error", cause = e)
    }
}

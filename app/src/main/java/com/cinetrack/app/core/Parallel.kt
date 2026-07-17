package com.cinetrack.app.core

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

/**
 * Dipindah dari kata/03_Coroutines.kt — pola dan implementasinya SAMA PERSIS
 * dengan yang kamu selesaikan sendiri di Phase 0 (async + awaitAll untuk
 * paralelisme sungguhan, bukan sequential). Bedanya cuma sekarang lokasinya
 * di core/ supaya bisa dipakai kode produksi (MovieRepositoryImpl untuk
 * fitur detail film).
 *
 * Tidak ada bug yang perlu difix di sini (beda dari safeApiCall yang punya
 * bug CancellationException) — implementasi async/awaitAll kamu di kata #3
 * sudah otomatis benar soal cancellation, karena coroutineScope + async
 * memang dirancang untuk itu dari awal.
 */
suspend fun <A, B, R> fetchParallel(
    fetchA: suspend () -> A,
    fetchB: suspend () -> B,
    combine: (A, B) -> R
): R = coroutineScope {
    val deferredA = async { fetchA() }
    val deferredB = async { fetchB() }
    combine(deferredA.await(), deferredB.await())
}
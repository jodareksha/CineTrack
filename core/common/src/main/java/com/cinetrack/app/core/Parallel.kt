package com.cinetrack.app.core

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope


suspend fun <A, B, R> fetchParallel(
    fetchA: suspend () -> A,
    fetchB: suspend () -> B,
    combine: (A, B) -> R
): R = coroutineScope {
    val deferredA = async { fetchA() }
    val deferredB = async { fetchB() }
    combine(deferredA.await(), deferredB.await())
}
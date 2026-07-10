package com.cinetrack.app.kata

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope


object CoroutineKata {

    suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
        return try {
            Result.Success(apiCall())
        } catch (e: Exception) {
            if (e is kotlinx.coroutines.CancellationException) throw e
            Result.Error(message = e.message ?: "Unknown Error", cause = e)
        }
    }

    suspend fun <A, B, R> fetchParallel(
        fetchA: suspend () -> A,
        fetchB: suspend () -> B,
        combine: (A, B) -> R
    ): R = coroutineScope {
        val deferredA = async { fetchA() }
        val deferredB = async { fetchB() }
        combine(deferredA.await(), deferredB.await())

    }
}

package com.cinetrack.app.core


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

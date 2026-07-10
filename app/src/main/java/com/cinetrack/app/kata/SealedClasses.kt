package com.cinetrack.app.kata

sealed interface Result<out T> {
    object Loading : Result<Nothing>
    data class Success<T>(val data: T) : Result<T>
    data class Error(val message: String, val cause: Throwable? = null) : Result<Nothing>
}

object ResultKata {

    fun <T> describe(result: Result<T>): String {
     return when (result) {
            is Result.Loading -> "Loading..."
            is Result.Success -> "Data: ${result.data}"
            is Result.Error -> "Error: ${result.message}"
        }
    }

    fun <T, R> map(result: Result<T>, transform: (T) -> R): Result<R> {
        return when (result) {
            is Result.Success -> Result.Success(transform(result.data))
            is Result.Loading -> Result.Loading
            is Result.Error -> Result.Error(result.message)
        }
    }
}

package com.cinetrack.app.kata

data class MoviePoster(
    val title: String,
    var posterUrl: String? = null,
    var isFavorite: Boolean = false
)

object ScopeFunctionKata {

    fun buildFavoritePoster(title: String, posterUrlValue: String): MoviePoster {
        return MoviePoster(title).apply {
            posterUrl = posterUrlValue
            isFavorite = true
        }
    }

    fun resolvePosterUrlOrDefault(posterUrl: String?): String {
        return  posterUrl?.let { it.uppercase()} ?: "no_image.png"
    }

    fun logAndReturn(poster: MoviePoster, logs: MutableList<String>): MoviePoster {
        return poster.also { logs.add(it.title) }
    }

    fun computeDisplayScore(poster: MoviePoster): Int {
        return poster.run{
            title.length * if (isFavorite) 2 else 1
        }
    }
}
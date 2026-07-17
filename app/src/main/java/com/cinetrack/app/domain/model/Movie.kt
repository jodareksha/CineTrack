package com.cinetrack.app.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val posterUrl: String?,
    val ratingLabel: String,
    val releaseYear: String,
    val overview: String
)

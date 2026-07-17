package com.cinetrack.app.domain.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String?,
    val ratingLabel: String,
    val releaseYear: String,
    val runtimeLabel: String,
    val genres: List<String>,
    val castNames: List<String>
)
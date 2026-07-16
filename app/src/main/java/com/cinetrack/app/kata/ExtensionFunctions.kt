package com.cinetrack.app.kata

import java.util.Locale


fun String.toReleaseYear(): String {
    if (this.isEmpty() || !this.contains("-")) return "Unknown"

    val year = this.substringBefore("-")
    return if (year.length == 4 && year.all { it.isDigit() }) year else "Unknown"
}

fun Double.toRatingLabel(): String {
    val formattedRating = String.format(Locale.US, "%.1f", this)
    return "$formattedRating ★"
}

fun <T> List<T>.takeUpTo(n: Int): List<T> {
    return this.take(n)
}
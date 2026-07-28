
package com.cinetrack.app.core



fun String.toReleaseYear(): String {

    if (this.isEmpty() || !this.contains("-")) return "Unknown"

    val year = this.substringBefore("-")

    return if (year.length == 4 && year.all { it.isDigit() }) year else "Unknown"

}

fun Double.toRatingLabel(): String {

    val formattedRating = String.format(java.util.Locale.US, "%.1f", this)

    return "$formattedRating \u2605"

}

fun <T> List<T>.takeUpTo(n: Int): List<T> = this.take(n)


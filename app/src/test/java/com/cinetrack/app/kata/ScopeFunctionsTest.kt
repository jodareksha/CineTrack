package com.cinetrack.app.kata

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ScopeFunctionsTest {

    @Test
    fun `buildFavoritePoster creates poster with apply`() {
        val poster = ScopeFunctionKata.buildFavoritePoster("Dune", "dune.png")
        assertEquals("Dune", poster.title)
        assertEquals("dune.png", poster.posterUrl)
        assertTrue(poster.isFavorite)
    }

    @Test
    fun `resolvePosterUrlOrDefault returns uppercase when present`() {
        val result = ScopeFunctionKata.resolvePosterUrlOrDefault("dune.png")
        assertEquals("DUNE.PNG", result)
    }

    @Test
    fun `resolvePosterUrlOrDefault returns default when null`() {
        val result = ScopeFunctionKata.resolvePosterUrlOrDefault(null)
        assertEquals("no_image.png", result)
    }

    @Test
    fun `logAndReturn logs title and returns same poster`() {
        val logs = mutableListOf<String>()
        val poster = MoviePoster("Dune", "dune.png")
        val result = ScopeFunctionKata.logAndReturn(poster, logs)
        assertEquals(poster, result)
        assertEquals(listOf("Dune"), logs)
    }

    @Test
    fun `computeDisplayScore doubles score for favorites`() {
        val favorite = MoviePoster("Dune", isFavorite = true) // 4 chars * 2 = 8
        val nonFavorite = MoviePoster("Dune", isFavorite = false) // 4 chars * 1 = 4
        assertEquals(8, ScopeFunctionKata.computeDisplayScore(favorite))
        assertEquals(4, ScopeFunctionKata.computeDisplayScore(nonFavorite))
    }
}

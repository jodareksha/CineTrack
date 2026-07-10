package com.cinetrack.app.kata

import org.junit.Assert.assertEquals
import org.junit.Test

class ExtensionFunctionsTest {

    @Test
    fun `toReleaseYear extracts year correctly`() {
        assertEquals("2024", "2024-03-15".toReleaseYear())
    }

    @Test
    fun `toReleaseYear returns Unknown for empty string`() {
        assertEquals("Unknown", "".toReleaseYear())
    }

    @Test
    fun `toReleaseYear returns Unknown for invalid format`() {
        assertEquals("Unknown", "notadate".toReleaseYear())
    }

    @Test
    fun `toRatingLabel formats with one decimal and star`() {
        assertEquals("7.8 \u2605", 7.845.toRatingLabel())
    }

    @Test
    fun `toRatingLabel handles whole numbers`() {
        assertEquals("8.0 \u2605", 8.0.toRatingLabel())
    }

    @Test
    fun `takeUpTo returns first n items when list is longer`() {
        assertEquals(listOf(1, 2, 3), listOf(1, 2, 3, 4, 5).takeUpTo(3))
    }

    @Test
    fun `takeUpTo returns whole list when shorter than n`() {
        assertEquals(listOf(1, 2), listOf(1, 2).takeUpTo(5))
    }
}

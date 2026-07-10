package com.cinetrack.app.kata

import org.junit.Assert.assertEquals
import org.junit.Test

class SealedClassesTest {

    @Test
    fun `describe handles Loading`() {
        val result: Result<String> = Result.Loading
        assertEquals("Loading...", ResultKata.describe(result))
    }

    @Test
    fun `describe handles Success`() {
        val result: Result<String> = Result.Success("Dune")
        assertEquals("Data: Dune", ResultKata.describe(result))
    }

    @Test
    fun `describe handles Error`() {
        val result: Result<String> = Result.Error("network down")
        assertEquals("Error: network down", ResultKata.describe(result))
    }

    @Test
    fun `map transforms Success data`() {
        val result: Result<Int> = Result.Success(4)
        val mapped = ResultKata.map(result) { it * 2 }
        assertEquals(Result.Success(8), mapped)
    }

    @Test
    fun `map passes through Loading unchanged`() {
        val result: Result<Int> = Result.Loading
        val mapped = ResultKata.map(result) { it * 2 }
        assertEquals(Result.Loading, mapped)
    }

    @Test
    fun `map passes through Error unchanged`() {
        val result: Result<Int> = Result.Error("boom")
        val mapped = ResultKata.map(result) { it * 2 }
        assertEquals(Result.Error("boom"), mapped)
    }
}

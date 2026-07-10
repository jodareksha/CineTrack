package com.cinetrack.app.kata

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.system.measureTimeMillis

class CoroutinesTest {

    @Test
    fun `safeApiCall returns Success when apiCall succeeds`() = runBlocking {
        val result = CoroutineKata.safeApiCall { "Dune" }
        assertEquals(Result.Success("Dune"), result)
    }

    @Test
    fun `safeApiCall returns Error when apiCall throws`() = runBlocking {
        val result = CoroutineKata.safeApiCall<String> { throw RuntimeException("network down") }
        assertTrue(result is Result.Error)
        assertEquals("network down", (result as Result.Error).message)
    }

    @Test
    fun `fetchParallel combines both results correctly`() = runBlocking {
        val result = CoroutineKata.fetchParallel(
            fetchA = { 4 },
            fetchB = { "movies" },
            combine = { a, b -> "$b: $a" }
        )
        assertEquals("movies: 4", result)
    }

    @Test
    fun `fetchParallel runs both calls concurrently, not sequentially`() = runBlocking {
        // Jika diimplementasikan dengan benar (paralel), total waktu harus mendekati
        // 200ms (waktu delay terlama), BUKAN 400ms (jumlah kedua delay jika sequential).
        val elapsed = measureTimeMillis {
            CoroutineKata.fetchParallel(
                fetchA = { delay(200); "A" },
                fetchB = { delay(200); "B" },
                combine = { a, b -> "$a$b" }
            )
        }
        assertTrue(
            "Diharapkan berjalan paralel (~200ms), tapi memakan waktu ${elapsed}ms — cek apakah kamu pakai async{} atau malah memanggil berurutan",
            elapsed < 350
        )
    }
}

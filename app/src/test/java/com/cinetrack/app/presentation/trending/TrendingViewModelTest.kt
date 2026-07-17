package com.cinetrack.app.presentation.trending

import com.cinetrack.app.core.Result
import com.cinetrack.app.domain.model.Movie
import com.cinetrack.app.testutil.FakeMovieRepository
import com.cinetrack.app.testutil.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test



class TrendingViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val dummyMovies = listOf(
        Movie(
            id = 1,
            title = "Dune: Part Three",
            posterUrl = "https://image.tmdb.org/t/p/w342/dune3.jpg",
            ratingLabel = "8.2 \u2605",
            releaseYear = "2026",
            overview = "..."
        )
    )

    @Test
    fun `uiState becomes Success when repository returns data`() = runTest {
        val viewModel = TrendingViewModel(FakeMovieRepository(Result.Success(dummyMovies)))

        val state = viewModel.uiState.value

        assertTrue("Expected Success but was $state", state is TrendingUiState.Success)
        assertEquals(dummyMovies, (state as TrendingUiState.Success).movies)
    }

    @Test
    fun `uiState becomes Error when repository fails`() = runTest {
        val viewModel = TrendingViewModel(FakeMovieRepository(Result.Error("network down")))

        val state = viewModel.uiState.value

        assertTrue("Expected Error but was $state", state is TrendingUiState.Error)
        assertEquals("network down", (state as TrendingUiState.Error).message)
    }

    @Test
    fun `loadTrendingMovies can be called again to refresh`() = runTest {
        val viewModel = TrendingViewModel(FakeMovieRepository(Result.Success(dummyMovies)))

        // Panggil ulang secara eksplisit (mensimulasikan pull-to-refresh nanti)
        viewModel.loadTrendingMovies()

        val state = viewModel.uiState.value
        assertTrue(state is TrendingUiState.Success)
    }
}

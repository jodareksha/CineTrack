package com.cinetrack.app.presentation.search

import com.cinetrack.app.core.Result
import com.cinetrack.app.domain.model.Movie
import com.cinetrack.app.testutil.FakeMovieRepository
import com.cinetrack.app.testutil.MainDispatcherRule
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val dummyMovies = listOf(
        Movie(
            id = 2,
            title = "Batman: Fading Knight",
            posterUrl = null,
            ratingLabel = "7.5 \u2605",
            releaseYear = "2026",
            overview = "..."
        )
    )

    @Test
    fun `uiState is Idle before user types anything`() = runTest {
        val viewModel = SearchViewModel(FakeMovieRepository())
        assertEquals(SearchUiState.Idle, viewModel.uiState.value)
    }

    @Test
    fun `typing a query eventually emits Success after debounce`() = runTest {
        val viewModel = SearchViewModel(
            FakeMovieRepository(searchResult = Result.Success(dummyMovies))
        )
        val emissions = mutableListOf<SearchUiState>()

        val job = launch { viewModel.uiState.collect { emissions.add(it) } }

        viewModel.onQueryChanged("batman")
        advanceTimeBy(500)
        runCurrent()


        assertTrue("Emissions: $emissions", emissions.last() is SearchUiState.Success)
        assertEquals(dummyMovies, (emissions.last() as SearchUiState.Success).movies)

        job.cancel()
    }

    @Test
    fun `empty search results emit Empty, not Success`() = runTest {
        val viewModel = SearchViewModel(
            FakeMovieRepository(searchResult = Result.Success(emptyList()))
        )
        val emissions = mutableListOf<SearchUiState>()
        val job = launch { viewModel.uiState.collect { emissions.add(it) } }

        viewModel.onQueryChanged("zzzznotfound")
        advanceTimeBy(500)
        runCurrent()

        assertEquals(SearchUiState.Empty, emissions.last())

        job.cancel()
    }

    @Test
    fun `blank query resets to Idle without calling repository`() = runTest {
        val viewModel = SearchViewModel(FakeMovieRepository())
        val emissions = mutableListOf<SearchUiState>()
        val job = launch { viewModel.uiState.collect { emissions.add(it) } }

        viewModel.onQueryChanged("   ")
        advanceTimeBy(500)
        runCurrent()

        assertEquals(SearchUiState.Idle, emissions.last())

        job.cancel()
    }
}
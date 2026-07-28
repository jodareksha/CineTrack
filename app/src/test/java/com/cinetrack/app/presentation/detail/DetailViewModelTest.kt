package com.cinetrack.app.presentation.detail

import androidx.lifecycle.SavedStateHandle
import com.cinetrack.app.core.Result
import com.cinetrack.app.domain.model.MovieDetail
import com.cinetrack.app.testutil.FakeMovieRepository
import com.cinetrack.app.testutil.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class DetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val dummyDetail = MovieDetail(
        id = 42,
        title = "Dune: Part Three",
        overview = "...",
        posterUrl = null,
        ratingLabel = "8.2 \u2605",
        releaseYear = "2026",
        runtimeLabel = "166 min",
        genres = listOf("Sci-Fi", "Adventure"),
        castNames = listOf("Timothee Chalamet")
    )

    private fun savedStateHandleWithMovieId(id: Int) =
        SavedStateHandle(mapOf("movieId" to id))

    @Test
    fun `uiState becomes Success when repository returns detail`() = runTest {
        val viewModel = DetailViewModel(
            repository = FakeMovieRepository(detailResult = Result.Success(dummyDetail)),
            savedStateHandle = savedStateHandleWithMovieId(42)
        )

        val state = viewModel.uiState.value

        Assert.assertTrue("Expected Success but was $state", state is DetailUiState.Success)
        Assert.assertEquals(dummyDetail, (state as DetailUiState.Success).detail)
    }

    @Test
    fun `uiState becomes Error when repository fails`() = runTest {
        val viewModel = DetailViewModel(
            repository = FakeMovieRepository(detailResult = Result.Error("not found")),
            savedStateHandle = savedStateHandleWithMovieId(42)
        )

        val state = viewModel.uiState.value

        Assert.assertTrue(state is DetailUiState.Error)
        Assert.assertEquals("not found", (state as DetailUiState.Error).message)
    }
}
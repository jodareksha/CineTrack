package com.cinetrack.app.presentation.trending

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import com.cinetrack.app.domain.model.Movie
import org.junit.Rule
import org.junit.Test


class TrendingContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val dummyMovies = listOf(
        Movie(1, "Dune: Part Three", null, "8.2 \u2605", "2026", "...")
    )

    @Test
    fun loadingState_showsProgressIndicator() {
        composeTestRule.setContent {
            MaterialTheme{
                TrendingContent(uiState = TrendingUiState.Loading)
            }
        }

        composeTestRule.onNodeWithContentDescription("Loading").assertIsDisplayed()
    }

    @Test
    fun successState_showsMovieTitle() {
        composeTestRule.setContent {
            MaterialTheme {
                TrendingContent(uiState = TrendingUiState.Success(movies = dummyMovies))
            }
        }


        composeTestRule.onNodeWithText("Dune: Part Three").assertIsDisplayed()
    }

    @Test
    fun errorState_showsErrorMessage() {
        composeTestRule.setContent {
            MaterialTheme {
                TrendingContent(uiState = TrendingUiState.Error("Gagal memuat data"))
            }
        }

        composeTestRule.onNodeWithText("Gagal memuat data").assertIsDisplayed()
    }
}
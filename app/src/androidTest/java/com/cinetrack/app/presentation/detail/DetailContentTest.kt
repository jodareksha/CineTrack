package com.cinetrack.app.presentation.detail

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import com.cinetrack.app.domain.model.MovieDetail
import com.cinetrack.app.presentation.trending.TrendingContent
import com.cinetrack.app.presentation.trending.TrendingUiState
import org.junit.Rule
import org.junit.Test

class DetailContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val dummyDetail = MovieDetail(
        id = 1,
        title = "The Odyssey",
        overview = "Odysseus, the legendary King of Ithaca, embarks on a long journey.",
        posterUrl = null,
        ratingLabel = "8.0 \u2605",
        releaseYear = "2026",
        runtimeLabel = "173 min",
        genres = listOf("Adventure", "Action", "Fantasy"),
        castNames = listOf("Matt Damon", "Tom Holland")
    )

    @Test
    fun loadingState_showsProgressIndicator() {
        composeTestRule.setContent {
            MaterialTheme{
                DetailContent(uiState = DetailUiState.Loading)
            }
        }
        composeTestRule.onNodeWithContentDescription("Loading").assertIsDisplayed()
    }

    @Test
    fun errorState_showsErrorMessage() {
        composeTestRule.setContent {
            MaterialTheme {
                DetailContent(uiState = DetailUiState.Error("not found"))
            }
        }

        composeTestRule.onNodeWithText("not found").assertIsDisplayed()
    }


    @Test
    fun successState_showsMovieTitle() {
        composeTestRule.setContent {
            MaterialTheme {
                DetailContent(uiState = DetailUiState.Success(detail = dummyDetail))
            }
        }

        composeTestRule.onNodeWithText("The Odyssey").assertIsDisplayed()
        composeTestRule.onNodeWithText("Adventure").assertIsDisplayed()
    }
}
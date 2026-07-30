package com.cinetrack.app.presentation.search

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.performTextInput
import com.cinetrack.app.domain.model.Movie
import org.junit.Rule
import org.junit.Test

class SearchContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val dummyMovies = listOf(
        Movie(1, "Batman: Fading Knight", null, "7.5 \u2605", "2026", "...")
    )

    @Test
    fun idleState_showsPlaceholderText() {
        composeTestRule.setContent {
            MaterialTheme{
                SearchContent(query = "", onQueryChange = {}, uiState = SearchUiState.Idle)
            }
        }
        composeTestRule.onNodeWithText("Ketik untuk mencari film").assertIsDisplayed()

    }

    @Test
    fun successState_showsMovieTitle() {
        composeTestRule.setContent {
            MaterialTheme {
                SearchContent(
                    query = "",
                    onQueryChange = {},
                    uiState = SearchUiState.Success(movies = dummyMovies)
                )
            }
        }

        composeTestRule.onNodeWithText("Batman: Fading Knight").assertIsDisplayed()
    }


    @Test
    fun typingInSearchBar_triggersOnQueryChange() {
        var capturedQuery = ""

        composeTestRule.setContent {
            MaterialTheme {
                SearchContent(
                    query = "",
                    onQueryChange = { newQuery -> capturedQuery = newQuery },
                    uiState = SearchUiState.Idle
                )
            }
        }

        composeTestRule.onNodeWithText("Cari judul film...").performTextInput("dune")

        assert(capturedQuery == "dune") { "Expected 'dune' but was '$capturedQuery'" }
    }
}
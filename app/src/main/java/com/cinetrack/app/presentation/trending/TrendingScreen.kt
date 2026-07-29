package com.cinetrack.app.presentation.trending

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cinetrack.app.domain.model.Movie
import com.cinetrack.app.presentation.common.MoviePosterCard
import androidx.compose.foundation.lazy.grid.items

@Composable
fun TrendingScreen(
    viewModel: TrendingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TrendingContent(uiState = uiState)
}


@Composable
private fun TrendingContent(uiState: TrendingUiState) {
    when (uiState) {
        is TrendingUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is TrendingUiState.Success -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.movies, key = { it.id }) { movie ->
                    MoviePosterCard(movie)
                }
            }
        }
        is TrendingUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = uiState.message)
            }
        }
    }
}



@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun TrendingContentSuccessPreview() {
    MaterialTheme {
        TrendingContent(
            uiState = TrendingUiState.Success(
                movies = listOf(
                    Movie(1, "The Odyssey", null, "6.2 \u2605", "2026", "..."),
                    Movie(2, "Backrooms", null, "7.0 \u2605", "2026", "..."),
                    Movie(3, "Obsession", null, "8.2 \u2605", "2026", "...")
                )
            )
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun TrendingContentLoadingPreview() {
    MaterialTheme {
        TrendingContent(uiState = TrendingUiState.Loading)
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun TrendingContentErrorPreview() {
    MaterialTheme {
        TrendingContent(uiState = TrendingUiState.Error("Gagal memuat data"))
    }
}
package com.cinetrack.app.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cinetrack.app.domain.model.Movie
import com.cinetrack.app.presentation.common.MoviePosterCard

@Composable
private fun SearchContent(
    query: String,
    onQueryChange: (String) -> Unit,
    uiState: SearchUiState,
    onMovieClick: (Movie) -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(query = query, onQueryChange = onQueryChange)

        when (uiState) {
            is SearchUiState.Idle -> IdleState()
            is SearchUiState.Loading -> LoadingState()
            is SearchUiState.Success -> SuccessState(movies = uiState.movies, onMovieClick = onMovieClick)
            is SearchUiState.Empty -> EmptyState()
            is SearchUiState.Error -> ErrorState(message = uiState.message)
        }
    }
}

@Composable
private fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    Surface(shadowElevation = 8.dp) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = { Text("Cari judul film...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
    }
}

@Composable
private fun IdleState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Ketik untuk mencari film")
    }
}

@Composable
private fun LoadingState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(Color(0xFF212121), shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(color = Color.White, strokeWidth = 4.dp)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Loading...",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun SuccessState(movies: List<Movie>, onMovieClick: (Movie) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(movies, key = { it.id }) { movie ->
            MoviePosterCard(movie, onClick = onMovieClick)
        }
    }
}

@Composable
private fun EmptyState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Film tidak ditemukan")
    }
}

@Composable
private fun ErrorState(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = message)
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun SearchContentIdlePreview() {
    MaterialTheme {
        SearchContent(query = "", onQueryChange = {}, uiState = SearchUiState.Idle)
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun SearchContentLoadingPreview() {
    MaterialTheme {
        SearchContent(query = "batman", onQueryChange = {}, uiState = SearchUiState.Loading)
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun SearchContentSuccessPreview() {
    MaterialTheme {
        SearchContent(
            query = "batman",
            onQueryChange = {},
            uiState = SearchUiState.Success(
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
private fun SearchContentErrorPreview() {
    MaterialTheme {
        SearchContent(query = "batman", onQueryChange = {}, uiState = SearchUiState.Error("Error Data"))
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun SearchContentEmptyPreview() {
    MaterialTheme {
        SearchContent(query = "zzzznotfound", onQueryChange = {}, uiState = SearchUiState.Empty)
    }
}

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onMovieClick: (Movie) -> Unit = {}
) {
    var query by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SearchContent(
        query = query,
        onQueryChange = { newQuery ->
            query = newQuery
            viewModel.onQueryChanged(newQuery)
        },
        uiState = uiState,
        onMovieClick = onMovieClick
    )
}
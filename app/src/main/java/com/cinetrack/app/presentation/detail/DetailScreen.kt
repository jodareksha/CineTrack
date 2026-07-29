package com.cinetrack.app.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cinetrack.app.domain.model.MovieDetail

@Composable
private fun DetailContent(uiState: DetailUiState) {
    when (uiState) {
        is DetailUiState.Loading -> LoadingState()
        is DetailUiState.Error -> ErrorState(message = uiState.message)
        is DetailUiState.Success -> SuccessState(detail = uiState.detail)
    }
}

@Composable
private fun LoadingState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorState(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = message)
    }
}

@Composable
private fun SuccessState(detail: MovieDetail) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        PosterHeader(detail)

        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = detail.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            InfoChipsRow(detail)

            if (detail.genres.isNotEmpty()) {
                GenresSection(detail.genres)
            }

            HorizontalDivider()

            OverviewSection(detail.overview)

            if (detail.castNames.isNotEmpty()) {
                HorizontalDivider()
                CastSection(detail.castNames)
            }
        }
    }
}

@Composable
private fun PosterHeader(detail: MovieDetail) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(detail.posterUrl)
            .crossfade(true)
            .build(),
        contentDescription = detail.title,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    )
}

@Composable
private fun InfoChipsRow(detail: MovieDetail) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DetailInfoChip(text = detail.ratingLabel)
        DetailInfoChip(text = detail.releaseYear)
        if (detail.runtimeLabel.isNotBlank()) {
            DetailInfoChip(text = detail.runtimeLabel)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun GenresSection(genres: List<String>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Genres",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            genres.forEach { genre ->
                SuggestionChip(onClick = {}, label = { Text(text = genre) })
            }
        }
    }
}

@Composable
private fun OverviewSection(overview: String) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Overview",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = overview,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 24.sp
        )
    }
}

@Composable
private fun CastSection(castNames: List<String>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Cast",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )
        castNames.forEach { castName -> CastRow(castName) }
    }
}

@Composable
private fun CastRow(castName: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = castName.firstOrNull()?.uppercase().orEmpty(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.Bold
            )
        }
        Text(text = castName, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun DetailInfoChip(text: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun DetailContentLoadingPreview() {
    MaterialTheme { DetailContent(uiState = DetailUiState.Loading) }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 840)
@Composable
private fun DetailContentSuccessPreview() {
    MaterialTheme {
        DetailContent(
            uiState = DetailUiState.Success(
                detail = MovieDetail(
                    id = 1,
                    title = "The Odyssey",
                    overview = "Odysseus, the legendary King of Ithaca, embarks on a long and perilous journey home following the Trojan War.",
                    posterUrl = null,
                    ratingLabel = "8.0 \u2605",
                    releaseYear = "2026",
                    runtimeLabel = "173 min",
                    genres = listOf("Adventure", "Action", "Fantasy"),
                    castNames = listOf("Matt Damon", "Tom Holland", "Anne Hathaway")
                )
            )
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun DetailContentErrorPreview() {
    MaterialTheme { DetailContent(uiState = DetailUiState.Error("Not Found")) }
}

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DetailContent(uiState = uiState)
}
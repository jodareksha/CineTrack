package com.cinetrack.app.presentation.trending

import com.cinetrack.app.domain.model.Movie

sealed interface TrendingUiState {
    data object Loading : TrendingUiState
    data class Success(val movies: List<Movie>) : TrendingUiState
    data class Error(val message: String) : TrendingUiState
}

package com.cinetrack.app.presentation.search

import com.cinetrack.app.domain.model.Movie


sealed interface SearchUiState {
    data object Idle : SearchUiState
    data object Loading : SearchUiState
    data class Success(val movies: List<Movie>) : SearchUiState
    data object Empty : SearchUiState
    data class Error(val message: String) : SearchUiState
}
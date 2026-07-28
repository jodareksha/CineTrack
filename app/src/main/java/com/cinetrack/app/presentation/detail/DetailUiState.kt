package com.cinetrack.app.presentation.detail

import com.cinetrack.app.domain.model.MovieDetail

sealed interface DetailUiState {
    data object Loading : DetailUiState
    data class Success(val detail: MovieDetail) : DetailUiState
    data class Error(val message: String) : DetailUiState
}
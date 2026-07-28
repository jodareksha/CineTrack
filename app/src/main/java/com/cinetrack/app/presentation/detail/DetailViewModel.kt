package com.cinetrack.app.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cinetrack.app.core.Result
import com.cinetrack.app.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MovieRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId: Int = savedStateHandle.get<Int>("movieId")
        ?: error("DetailViewModel requires a movieId argument")

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        loadMovieDetail()
    }

    fun loadMovieDetail() {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading

            when (val result = repository.getMovieDetail(movieId)) {
                is Result.Success -> {
                    _uiState.value = DetailUiState.Success(detail = result.data)
                }
                is Result.Error -> {
                    _uiState.value = DetailUiState.Error(message = result.message)
                }
                Result.Loading -> {
                    _uiState.value = DetailUiState.Loading
                }
            }
        }
    }
}
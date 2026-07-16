package com.cinetrack.app.presentation.trending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cinetrack.app.core.Result
import com.cinetrack.app.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.annotation.meta.When
import javax.inject.Inject


@HiltViewModel
class TrendingViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<TrendingUiState>(TrendingUiState.Loading)
    val uiState: StateFlow<TrendingUiState> = _uiState.asStateFlow()

    init {
        loadTrendingMovies()
    }

    fun loadTrendingMovies() {
        viewModelScope.launch {

            _uiState.value = TrendingUiState.Loading

            when (val result = repository.getTrendingMovies()){
                is Result.Success -> {
                    _uiState.value = TrendingUiState.Success(movies = result.data)
                }
                is Result.Error -> {
                    _uiState.value = TrendingUiState.Error(message = result.message)
                }

                Result.Loading -> {
                    _uiState.value = TrendingUiState.Loading
                }
            }
        }
    }
}

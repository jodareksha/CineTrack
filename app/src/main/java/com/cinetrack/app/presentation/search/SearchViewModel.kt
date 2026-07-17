package com.cinetrack.app.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cinetrack.app.core.Result
import com.cinetrack.app.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    // _query menampung apa yang user ketik, di-update tiap kali karakter berubah.
    // Ini SUMBER dari seluruh chain Flow di bawah — mirip _uiState di TrendingViewModel,
    // tapi bedanya di sini yang kita simpan adalah INPUT user, bukan hasil akhir.
    private val _query = MutableStateFlow("")

    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
    }

    val uiState: StateFlow<SearchUiState> = _query
        .debounce(400)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            if (query.isBlank()) {
                flowOf<SearchUiState>(SearchUiState.Idle)
            } else {
                flow {
                    emit(SearchUiState.Loading)
                    when (val result = repository.searchMovies(query)){
                        is Result.Success -> emit(
                            if (result.data.isEmpty()) SearchUiState.Empty
                            else SearchUiState.Success(result.data)
                        )
                        is Result.Error -> emit(SearchUiState.Error(result.message))
                        Result.Loading -> emit(SearchUiState.Loading)

                    }
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SearchUiState.Idle
        )
}
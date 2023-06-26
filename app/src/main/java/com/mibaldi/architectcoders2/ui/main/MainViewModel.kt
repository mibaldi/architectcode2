package com.mibaldi.architectcoders2.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mibaldi.architectcoders2.data.toError
import com.mibaldi.architectcoders2.domain.Error
import com.mibaldi.architectcoders2.domain.Movie
import com.mibaldi.architectcoders2.usecases.GetPopularMoviesUseCase
import com.mibaldi.architectcoders2.usecases.RequestPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val requestPopularMoviesUseCase: RequestPopularMoviesUseCase
): ViewModel(){

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getPopularMoviesUseCase()
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { movies -> _state.update { UiState(movies = movies) }}
        }
    }
    fun onUiReady() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            val error = requestPopularMoviesUseCase()
            _state.value = _state.value.copy(loading = false, error = error)
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie>? = null,
        val error: Error? = null
    )
}

package com.amartinez.pokeapp.presentation.viewmodel.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amartinez.pokeapp.domain.usecase.detail.GetPokemonByIdUseCase
import com.amartinez.pokeapp.domain.usecase.home.MarkPokemonAsFavoriteUseCase
import com.amartinez.pokeapp.presentation.state.detail.DetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel @Inject constructor(
    val getPokemonByIdUseCase: GetPokemonByIdUseCase,
    val markPokemonAsFavoriteUseCase: MarkPokemonAsFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private var detailJob: Job? = null

    fun getPokemonById(id: Long) {
        detailJob?.cancel()
        _uiState.update { it.copy(isLoading = true, id = null) }
        detailJob = viewModelScope.launch(Dispatchers.IO) {
            getPokemonByIdUseCase(id).collect { response ->
                _uiState.update {
                    it.copy(
                        pokemon = response,
                        isLoading = false,
                        id = id
                    )
                }
            }
        }
    }

    fun markAsFavorite(id: Long, currentFavoriteStatus: Boolean) {
        viewModelScope.launch (Dispatchers.IO) {
            markPokemonAsFavoriteUseCase(id, !currentFavoriteStatus)
        }
    }
}
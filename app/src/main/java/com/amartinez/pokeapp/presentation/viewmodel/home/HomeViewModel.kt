package com.amartinez.pokeapp.presentation.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.amartinez.pokeapp.domain.usecase.home.FetchAllPokemonUseCase
import com.amartinez.pokeapp.domain.usecase.home.MarkPokemonAsFavoriteUseCase
import com.amartinez.pokeapp.domain.usecase.home.SearchPokemonUseCase
import com.amartinez.pokeapp.presentation.state.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchAllPokemonUseCase: FetchAllPokemonUseCase,
    private val searchPokemonUseCase: SearchPokemonUseCase,
    private val markPokemonAsFavoriteUseCase: MarkPokemonAsFavoriteUseCase
) : ViewModel() {
    //val pagerFlow = searchPokemonUseCase().cachedIn(viewModelScope)

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val isDone = fetchAllPokemonUseCase()
            if(isDone) {
                val pagerFlow = searchPokemonUseCase()
                _uiState.update { state ->
                    state.copy(
                        items = pagerFlow
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

    fun onFilterChange(value: String) {
        _uiState.update { state ->
            state.copy(
                filter = value
            )
        }
        val pagerFlow = searchPokemonUseCase()
        _uiState.update { state ->
            state.copy(
                items = pagerFlow
            )
        }
    }
}
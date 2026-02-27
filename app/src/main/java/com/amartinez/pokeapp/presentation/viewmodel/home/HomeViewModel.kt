package com.amartinez.pokeapp.presentation.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.amartinez.pokeapp.domain.model.SortOption
import com.amartinez.pokeapp.domain.usecase.home.FetchAllPokemonUseCase
import com.amartinez.pokeapp.domain.usecase.home.MarkPokemonAsFavoriteUseCase
import com.amartinez.pokeapp.domain.usecase.home.SearchPokemonUseCase
import com.amartinez.pokeapp.presentation.state.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchAllPokemonUseCase: FetchAllPokemonUseCase,
    private val searchPokemonUseCase: SearchPokemonUseCase,
    private val markPokemonAsFavoriteUseCase: MarkPokemonAsFavoriteUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            loadData()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val pokemonPagingData = combine(
        _searchQuery.debounce(300).distinctUntilChanged(),
        _uiState.map { it.sortBy }.distinctUntilChanged()
    ) { query, sort ->
        query to sort
    }.flatMapLatest { (query, sort) ->
        searchPokemonUseCase(query, sort)
    }.cachedIn(viewModelScope)

    fun markAsFavorite(id: Long, currentFavoriteStatus: Boolean) {
        viewModelScope.launch (Dispatchers.IO) {
            markPokemonAsFavoriteUseCase(id, !currentFavoriteStatus)
        }
    }

    fun onQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun loadData() {
        _uiState.update { state ->
            state.copy(
                isLoading = true,
                count = 0
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            val count = fetchAllPokemonUseCase()
            _uiState.update { state ->
                state.copy(
                    isLoading = false,
                    count = count
                )
            }
        }
    }

    fun sortItemsBy(sortBy: SortOption) {
        _uiState.update { state ->
            state.copy(
                sortBy = sortBy,
                showDialog = false
            )
        }
    }

    fun showDialog(show: Boolean) {
        _uiState.update { state ->
            state.copy(
                showDialog = show
            )
        }
    }
}
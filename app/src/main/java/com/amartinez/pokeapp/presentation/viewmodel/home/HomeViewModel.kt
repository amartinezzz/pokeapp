package com.amartinez.pokeapp.presentation.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.amartinez.pokeapp.domain.usecase.home.FetchAllPokemonUseCase
import com.amartinez.pokeapp.domain.usecase.home.MarkPokemonAsFavoriteUseCase
import com.amartinez.pokeapp.domain.usecase.home.SearchPokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchAllPokemonUseCase: FetchAllPokemonUseCase,
    private val searchPokemonUseCase: SearchPokemonUseCase,
    private val markPokemonAsFavoriteUseCase: MarkPokemonAsFavoriteUseCase
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetchAllPokemonUseCase()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val pokemonPagingData = _searchQuery
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            searchPokemonUseCase(query)
        }
        .cachedIn(viewModelScope)

    fun markAsFavorite(id: Long, currentFavoriteStatus: Boolean) {
        viewModelScope.launch (Dispatchers.IO) {
            markPokemonAsFavoriteUseCase(id, !currentFavoriteStatus)
        }
    }

    fun onQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }
}
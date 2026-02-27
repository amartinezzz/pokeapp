package com.amartinez.pokeapp.presentation.state.home

import com.amartinez.pokeapp.domain.model.SortOption

data class HomeUiState(
    val isLoading: Boolean = true,
    val count: Int = 0,
    val showDialog: Boolean = false,
    val sortBy: SortOption = SortOption.NUMBER_ASC
)
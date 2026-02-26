package com.amartinez.pokeapp.presentation.state.detail

import com.amartinez.pokeapp.domain.model.Pokemon

data class DetailUiState(
    val pokemon: Pokemon = Pokemon(),
    val isLoading: Boolean = true,
    val id: Long? = null
)
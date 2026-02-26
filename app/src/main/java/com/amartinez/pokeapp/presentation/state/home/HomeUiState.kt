package com.amartinez.pokeapp.presentation.state.home

import androidx.paging.PagingData
import com.amartinez.pokeapp.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

data class HomeUiState(
    val filter: String = "",
    val items:  Flow<PagingData<Pokemon>>? = null
)
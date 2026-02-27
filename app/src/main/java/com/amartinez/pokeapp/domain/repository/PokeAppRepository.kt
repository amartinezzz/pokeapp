package com.amartinez.pokeapp.domain.repository

import androidx.paging.PagingData
import com.amartinez.pokeapp.domain.model.Pokemon
import com.amartinez.pokeapp.domain.model.SortOption
import kotlinx.coroutines.flow.Flow

interface PokeAppRepository {
    suspend fun fetchPokemon(): Int

    suspend fun searchPokemonList(filter: String, sortBy: SortOption): Flow<PagingData<Pokemon>>

    suspend fun getPokemonDetailById(id: Long): Flow<Pokemon>

    suspend fun updateFavoriteStatus(id: Long, isFavorite: Boolean)
}
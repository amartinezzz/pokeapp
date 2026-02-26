package com.amartinez.pokeapp.domain.repository

import androidx.paging.PagingData
import com.amartinez.pokeapp.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokeAppRepository {
    suspend fun fetchPokemon(): Boolean

    fun searchPokemonList(filter: String): Flow<PagingData<Pokemon>>

    suspend fun getPokemonDetailById(id: Long): Flow<Pokemon>

    suspend fun updateFavoriteStatus(id: Long, isFavorite: Boolean)
}
package com.amartinez.pokeapp.data.remote.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.amartinez.pokeapp.data.local.database.PokeAppDB
import com.amartinez.pokeapp.data.local.entity.PokemonEntity
import com.amartinez.pokeapp.data.remote.api.PokeAppApi
import com.amartinez.pokeapp.data.remote.dto.toEntity

@OptIn(ExperimentalPagingApi::class)
class RemoteMediator(
    private val database: PokeAppDB,
    private val api: PokeAppApi
) : RemoteMediator<Int, PokemonEntity>() {
    override suspend fun initialize(): InitializeAction {
        return InitializeAction.SKIP_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {

        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    lastItem?.id ?: 0
                }
            }

            val response = api.fetchPokemon(offset = loadKey.toInt(), limit = state.config.pageSize)
            
            database.withTransaction {
                database.pokemonDao().insert(response.body()!!.toEntity())
            }

            MediatorResult.Success(endOfPaginationReached = response.body()?.results!!.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
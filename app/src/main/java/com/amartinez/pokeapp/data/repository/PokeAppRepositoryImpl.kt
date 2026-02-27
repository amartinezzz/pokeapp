package com.amartinez.pokeapp.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.amartinez.pokeapp.data.local.dao.PokeAppDao
import com.amartinez.pokeapp.data.local.entity.AbilityEntity
import com.amartinez.pokeapp.data.local.entity.PokemonEntity
import com.amartinez.pokeapp.data.local.entity.StatEntity
import com.amartinez.pokeapp.data.local.entity.TypeEntity
import com.amartinez.pokeapp.data.local.entity.toDomain
import com.amartinez.pokeapp.data.remote.api.PokeAppApi
import com.amartinez.pokeapp.data.remote.dto.toEntity
import com.amartinez.pokeapp.domain.model.Pokemon
import com.amartinez.pokeapp.domain.model.SortOption
import com.amartinez.pokeapp.domain.repository.PokeAppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PokeAppRepositoryImpl @Inject constructor(
    private val dao: PokeAppDao,
    private val api: PokeAppApi
) : PokeAppRepository {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun searchPokemonList(query: String, sortBy: SortOption) = Pager(
        config = PagingConfig(
            pageSize = 21,
            prefetchDistance = 5,
            initialLoadSize = 40,
            enablePlaceholders = true
        ),
        pagingSourceFactory = {
            dao.searchPokemonOrderByIdAsc(query)
            when (sortBy) {
                SortOption.NUMBER_ASC -> dao.searchPokemonOrderByIdAsc(query)
                SortOption.NUMBER_DESC -> dao.searchPokemonOrderByIdDesc(query)
                SortOption.NAME_ASC -> dao.searchPokemonOrderByNameAsc(query)
                SortOption.NAME_DESC -> dao.searchPokemonOrderByNameDesc(query)
                SortOption.FAVORITE_ASC -> dao.searchPokemonOrderByFavoriteAsc(query)
                SortOption.FAVORITE_DESC -> dao.searchPokemonOrderByFavoriteDesc(query)
            }
        }
    ).flow.map { pagingData: PagingData<PokemonEntity> ->
        pagingData.map { entity ->
            entity.toDomain()
        }
    }

    override suspend fun fetchPokemon(): Int {
        val count = dao.getPokemonCount()
        if (count > 0) return count

        return try {
            val response = api.fetchPokemon(limit = 1100, offset = 0)

            val entities = response.body()?.results?.map { remote ->
                val id = remote.url?.split("/")?.last { it.isNotEmpty() }?.toLong()
                PokemonEntity(
                    id = id ?: 0L,
                    name = remote.name ?: "-",
                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
                )
            }

            dao.insert(entities!!)
            entities.size
        } catch (_: Exception) {
            0
        }
    }

    override suspend fun getPokemonDetailById(id: Long): Flow<Pokemon> {
        val localPokemon = getPokemonDetailByIdFromDB(id)
        val isIncomplete = localPokemon == null || localPokemon.height == 0L

        if (isIncomplete) {
            try {
                getPokemonDetailByIdFromApi(id)
            } catch (_: Exception) {}
        }

        return dao.getPokemonById(id)
            .map { entity ->
                entity?.toDomain() ?: Pokemon()
            }
    }

    private fun getPokemonDetailByIdFromDB(id: Long): PokemonEntity? {
        return dao.searchPokemonById(id)
    }

    private suspend fun getPokemonDetailByIdFromApi(id: Long) {
        try {
            val responseDto = api.getPokemonDetailById(id)
            dao.updatePokemonById(
                id = id,
                abilities = responseDto.abilities.map { AbilityEntity(ability = it.ability?.toEntity()) },
                height = responseDto.height,
                stats = responseDto.stats.map {
                    StatEntity(
                        baseStat = it.baseStat,
                        stat = it.stat.toEntity()
                    )
                },
                types = responseDto.types.map { TypeEntity(type = it.type.toEntity()) },
                weight = responseDto.weight
            )
        } catch (e: Exception) {
            Log.e("PokeAppRepository", "Error fetching Pokemon information from API", e)
        }
    }

    override suspend fun updateFavoriteStatus(id: Long, isFavorite: Boolean) {
        dao.updateFavoriteStatus(id, isFavorite)
    }
}
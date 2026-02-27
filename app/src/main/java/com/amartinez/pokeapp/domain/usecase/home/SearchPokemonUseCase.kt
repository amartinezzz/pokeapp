package com.amartinez.pokeapp.domain.usecase.home

import androidx.paging.PagingData
import com.amartinez.pokeapp.domain.model.Pokemon
import com.amartinez.pokeapp.domain.model.SortOption
import com.amartinez.pokeapp.domain.repository.PokeAppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchPokemonUseCase @Inject constructor(
    private val pokeAppRepository: PokeAppRepository,
) {
    suspend operator fun invoke(query: String = "", sortBy: SortOption): Flow<PagingData<Pokemon>> =
        pokeAppRepository.searchPokemonList(query, sortBy)

}
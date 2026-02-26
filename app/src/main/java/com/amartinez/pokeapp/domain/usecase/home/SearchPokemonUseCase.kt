package com.amartinez.pokeapp.domain.usecase.home

import androidx.paging.PagingData
import com.amartinez.pokeapp.domain.model.Pokemon
import com.amartinez.pokeapp.domain.repository.PokeAppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchPokemonUseCase @Inject constructor(
    private val pokeAppRepository: PokeAppRepository,
) {
    operator fun invoke(filter: String = ""): Flow<PagingData<Pokemon>> =
        pokeAppRepository.searchPokemonList(filter)

}
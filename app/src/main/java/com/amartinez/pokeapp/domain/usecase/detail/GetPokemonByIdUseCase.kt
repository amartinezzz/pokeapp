package com.amartinez.pokeapp.domain.usecase.detail

import com.amartinez.pokeapp.domain.model.Pokemon
import com.amartinez.pokeapp.domain.repository.PokeAppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonByIdUseCase @Inject constructor(
    private val pokeAppRepository: PokeAppRepository,
) {
    suspend operator fun invoke(id: Long): Flow<Pokemon> =
        pokeAppRepository.getPokemonDetailById(id)
}
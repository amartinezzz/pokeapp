package com.amartinez.pokeapp.domain.usecase.home

import com.amartinez.pokeapp.domain.repository.PokeAppRepository
import javax.inject.Inject

class FetchAllPokemonUseCase @Inject constructor(
    private val pokeAppRepository: PokeAppRepository,
) {
    suspend operator fun invoke() : Boolean =
        pokeAppRepository.fetchPokemon()
}
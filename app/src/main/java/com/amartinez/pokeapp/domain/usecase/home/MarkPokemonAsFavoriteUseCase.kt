package com.amartinez.pokeapp.domain.usecase.home

import com.amartinez.pokeapp.domain.repository.PokeAppRepository
import javax.inject.Inject

class MarkPokemonAsFavoriteUseCase @Inject constructor(
    private val pokeAppRepository: PokeAppRepository,
) {
    suspend operator fun invoke(id: Long, isFavorite: Boolean) {
        pokeAppRepository.updateFavoriteStatus(id, isFavorite)
    }
}
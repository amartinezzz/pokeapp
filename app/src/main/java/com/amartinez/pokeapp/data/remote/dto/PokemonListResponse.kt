package com.amartinez.pokeapp.data.remote.dto

import com.amartinez.pokeapp.data.local.entity.PokemonEntity
import com.amartinez.pokeapp.domain.model.Pokemon

data class PokemonListResponse(
    val count: Int?,
    val next: String?,
    val previous: String?,
    val results: List<ResultDto>?
)

data class ResultDto(
    val name: String?,
    val url: String?,
)

fun PokemonListResponse.toDomain(): List<Pokemon> {
    return this.results?.map { result ->
        val id: Long = result.url?.split("/")?.dropLast(1)?.last()?.toLongOrNull() ?: 0

        Pokemon(
            id = id,
            name = result.name?.replaceFirstChar { it.uppercase() } ?: "Unknown",
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png",
        )
    } ?: emptyList()
}

fun PokemonListResponse.toEntity(): List<PokemonEntity> {
    return this.results?.map { result ->
        val id: Long = result.url?.split("/")?.dropLast(1)?.last()?.toLongOrNull() ?: 0

        PokemonEntity(
            id = id,
            name = result.name?.replaceFirstChar { it.uppercase() } ?: "Unknown",
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png",
        )
    } ?: emptyList()
}
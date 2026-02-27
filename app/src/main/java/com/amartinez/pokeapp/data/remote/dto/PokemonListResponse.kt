package com.amartinez.pokeapp.data.remote.dto

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
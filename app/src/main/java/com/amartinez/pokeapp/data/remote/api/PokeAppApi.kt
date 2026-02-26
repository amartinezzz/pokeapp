package com.amartinez.pokeapp.data.remote.api

import com.amartinez.pokeapp.data.remote.dto.PokemonDetailResponse
import com.amartinez.pokeapp.data.remote.dto.PokemonListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeAppApi {
    @GET("pokemon")
    suspend fun fetchPokemon(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): Response<PokemonListResponse>

    @GET("pokemon/{id}")
    suspend fun getPokemonDetailById(
        @Path("id") id: Long
    ): PokemonDetailResponse
}
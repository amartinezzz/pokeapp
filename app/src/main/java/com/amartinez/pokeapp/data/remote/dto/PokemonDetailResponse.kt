package com.amartinez.pokeapp.data.remote.dto

import com.amartinez.pokeapp.data.local.entity.AbilityEntity
import com.amartinez.pokeapp.data.local.entity.PokemonEntity
import com.amartinez.pokeapp.data.local.entity.PropertyDetailEntity
import com.amartinez.pokeapp.data.local.entity.StatEntity
import com.amartinez.pokeapp.data.local.entity.TypeEntity
import com.google.gson.annotations.SerializedName

data class PokemonDetailResponse(
    val id: Long,
    val name: String,
    val abilities: List<AbilityDto>,
    val height: Long,
    val stats: List<StatDto>,
    val types: List<TypeDto>,
    val weight: Long
)

data class AbilityDto(
    val ability: PropertyDetailDto? = null,
    val isHidden: Boolean,
    val slot: Long
)

data class StatDto(
    @SerializedName("base_stat") val baseStat: Long,
    val stat: PropertyDetailDto
)

data class TypeDto(
    val type: PropertyDetailDto
)

data class PropertyDetailDto(
    val name: String,
)

fun PropertyDetailDto.toEntity(): PropertyDetailEntity {
    return PropertyDetailEntity(
        name = name
    )
}

fun PokemonDetailResponse.toEntity(): PokemonEntity {
    return PokemonEntity(
        id = id,
        name = name,
        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png",
        abilities = abilities.map { item ->
            AbilityEntity(
                ability = item.ability?.toEntity()
            )
        }.toList(),
        height = height,
        stats = stats.map { item ->
            StatEntity(
                baseStat = item.baseStat,
                stat = item.stat.toEntity()
            )
        }.toList(),
        types = types.map { item ->
            TypeEntity(
                type = item.type.toEntity()
            )
        }.toList(),
        weight = weight
    )
}
package com.amartinez.pokeapp.data.remote.dto

import com.amartinez.pokeapp.data.local.entity.PropertyDetailEntity
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

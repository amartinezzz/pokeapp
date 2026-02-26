package com.amartinez.pokeapp.domain.model

data class Pokemon(
    val id: Long = 0,
    val name: String = "",
    val imageUrl: String = "",
    val abilities: List<Ability> = ArrayList(),
    val height: Long = 0,
    val stats: List<Stat> = ArrayList(),
    val types: List<Type> = ArrayList(),
    val weight: Long = 0,
    val isFavorite: Boolean = false
)

data class Ability (
    val ability: PropertyDetail? = null,
)

data class Stat (
    val baseStat: Long,
    val stat: PropertyDetail
)

data class Type (
    val type: PropertyDetail
)

data class PropertyDetail (
    val name: String
)
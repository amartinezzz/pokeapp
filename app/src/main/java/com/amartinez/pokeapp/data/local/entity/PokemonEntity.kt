package com.amartinez.pokeapp.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.amartinez.pokeapp.domain.model.Ability
import com.amartinez.pokeapp.domain.model.Pokemon
import com.amartinez.pokeapp.domain.model.PropertyDetail
import com.amartinez.pokeapp.domain.model.Stat
import com.amartinez.pokeapp.domain.model.Type

@Entity(tableName = "pokemon_table", indices = [Index(value = ["name"])])
data class PokemonEntity(
    @PrimaryKey val id: Long,
    val name: String = "",
    val imageUrl: String = "",
    val abilities: List<AbilityEntity> = ArrayList(),
    val height: Long = 0,
    val stats: List<StatEntity> = ArrayList(),
    val types: List<TypeEntity> = ArrayList(),
    val weight: Long = 0,
    val isFavorite: Boolean = false
)

data class AbilityEntity (
    val ability: PropertyDetailEntity? = null,
)

data class StatEntity (
    val baseStat: Long,
    val stat: PropertyDetailEntity
)

data class TypeEntity (
    val type: PropertyDetailEntity
)

data class PropertyDetailEntity (
    val name: String
)

fun PropertyDetailEntity.toDomain(): PropertyDetail {
    return PropertyDetail(
        name = name
    )
}

fun PokemonEntity.toDomain(): Pokemon {
    return Pokemon(
        id = id,
        name = name,
        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png",
        abilities = abilities.map { item ->
            Ability(
                ability = item.ability?.toDomain()
            )
        }.toList(),
        height = height,
        stats = stats.map { item ->
            Stat(
                baseStat = item.baseStat,
                stat = item.stat.toDomain()
            )
        }.toList(),
        types = types.map { item ->
            Type(
                type = item.type.toDomain()
            )
        }.toList(),
        weight = weight,
        isFavorite = isFavorite
    )
}
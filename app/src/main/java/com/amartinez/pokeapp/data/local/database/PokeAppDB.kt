package com.amartinez.pokeapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.amartinez.pokeapp.data.local.converters.PokemonConverters
import com.amartinez.pokeapp.data.local.dao.PokeAppDao
import com.amartinez.pokeapp.data.local.entity.PokemonEntity

@Database(entities = [PokemonEntity::class], version = 1)
@TypeConverters(PokemonConverters::class)
abstract class PokeAppDB : RoomDatabase() {
    abstract fun pokemonDao(): PokeAppDao
}
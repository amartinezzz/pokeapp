package com.amartinez.pokeapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amartinez.pokeapp.data.local.entity.AbilityEntity
import com.amartinez.pokeapp.data.local.entity.PokemonEntity
import com.amartinez.pokeapp.data.local.entity.StatEntity
import com.amartinez.pokeapp.data.local.entity.TypeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokeAppDao {

    @Query("SELECT COUNT(*) FROM pokemon_table")
    suspend fun getPokemonCount(): Int

    @Query("SELECT * FROM pokemon_table WHERE name LIKE '%' || :filter || '%' ORDER BY id ASC")
    fun searchPokemon(filter: String): PagingSource<Int, PokemonEntity>

    @Query("SELECT * FROM pokemon_table WHERE id = :id")
    fun getPokemonById(id: Long): Flow<PokemonEntity?>

    @Query("SELECT * FROM pokemon_table WHERE id = :id")
    fun searchPokemonById(id: Long): PokemonEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(characters: List<PokemonEntity>)

    @Query("UPDATE pokemon_table SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteStatus(id: Long, isFavorite: Boolean)

    @Query("UPDATE pokemon_table " +
            "SET abilities = :abilities, " +
            "height = :height, " +
            "stats = :stats, " +
            "types = :types, " +
            "weight = :weight " +
            "WHERE id = :id")
    suspend fun updatePokemonById(
        id: Long,
        abilities: List<AbilityEntity>,
        height: Long,
        stats: List<StatEntity>,
        types: List<TypeEntity>,
        weight: Long
    ): Int

    @Query("DELETE FROM pokemon_table")
    suspend fun clearAll()
}
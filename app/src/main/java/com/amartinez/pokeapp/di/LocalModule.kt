package com.example.rickandmortycomposepractice.di

import android.content.Context
import androidx.room.Room
import com.amartinez.pokeapp.data.local.dao.PokeAppDao
import com.amartinez.pokeapp.data.local.database.PokeAppDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PokeAppDB {
        return Room.databaseBuilder(
            context,
            PokeAppDB::class.java,
            "pokeapp_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCharacterDao(database: PokeAppDB): PokeAppDao {
        return database.pokemonDao()
    }
}
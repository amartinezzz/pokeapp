package com.amartinez.pokeapp.di

import com.amartinez.pokeapp.data.local.dao.PokeAppDao
import com.amartinez.pokeapp.data.remote.api.PokeAppApi
import com.amartinez.pokeapp.data.remote.repository.PokeAppRepositoryImpl
import com.amartinez.pokeapp.domain.repository.PokeAppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun provideAuthRepository(
        dao: PokeAppDao,
        api: PokeAppApi
    ): PokeAppRepository {
        return PokeAppRepositoryImpl(dao, api)
    }
}
package com.amartinez.pokeapp

import com.amartinez.pokeapp.domain.model.SortOption
import com.amartinez.pokeapp.domain.repository.PokeAppRepository
import com.amartinez.pokeapp.domain.usecase.home.SearchPokemonUseCase
import com.amartinez.pokeapp.presentation.viewmodel.home.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel

    @MockK
    lateinit var repository: PokeAppRepository
    private lateinit var searchPokemonUseCase: SearchPokemonUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        searchPokemonUseCase = SearchPokemonUseCase(repository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun sortBy() = runTest {
        val newSort = SortOption.NAME_DESC
        
        viewModel.sortItemsBy(newSort)
        advanceUntilIdle()
        
        assert(viewModel.uiState.value.sortBy == SortOption.NAME_DESC)
        verify { searchPokemonUseCase("", newSort) }
    }
}
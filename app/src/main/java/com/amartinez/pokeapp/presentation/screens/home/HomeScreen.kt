package com.amartinez.pokeapp.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.amartinez.pokeapp.presentation.components.BodyStructure
import com.amartinez.pokeapp.presentation.components.PokemonCardItem
import com.amartinez.pokeapp.presentation.components.TextFieldComponent
import com.amartinez.pokeapp.presentation.viewmodel.home.HomeViewModel

@Composable
fun HomeScreen(
    goToDetail: (Long?) -> Unit
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val query by viewModel.searchQuery.collectAsState()

    BodyStructure(header = {
        Row {
            TextFieldComponent(
                value = query,
                icon = Icons.Default.Search,
                placeholder = "Search",
                onValueChange = viewModel::onQueryChanged,
                hideCondition = true
            )
        }
    }) {
        LazyGridWithPagination(
            modifier = Modifier,
            viewModel = viewModel,
            goToDetail = goToDetail
        )
    }
}

@Composable
fun LazyGridWithPagination(
    modifier: Modifier,
    viewModel: HomeViewModel,
    goToDetail: (Long?) -> Unit
) {
    val pagedItems = viewModel.pokemonPagingData.collectAsLazyPagingItems()

    if(pagedItems.itemCount > 0) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = modifier
                .background(color = Color.White)
                .fillMaxWidth(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                count = pagedItems.itemCount,
                key = pagedItems.itemKey { it.id }
            ) { index ->
                val pokemon = pagedItems[index]
                PokemonCardItem(pokemon, goToDetail) { id ->
                    viewModel.markAsFavorite(id ?: 0, pokemon?.isFavorite == true)
                }
            }
        }
    } else {
        //TODO error
    }
}
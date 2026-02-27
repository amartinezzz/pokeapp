package com.amartinez.pokeapp.presentation.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.amartinez.pokeapp.R
import com.amartinez.pokeapp.presentation.components.BodyStructure
import com.amartinez.pokeapp.presentation.components.EmptyState
import com.amartinez.pokeapp.presentation.components.TextFieldComponent
import com.amartinez.pokeapp.presentation.screens.home.component.LazyGridWithPagination
import com.amartinez.pokeapp.presentation.screens.home.component.LoadingLazyGrid
import com.amartinez.pokeapp.presentation.screens.home.component.SortByDialog
import com.amartinez.pokeapp.presentation.viewmodel.home.HomeViewModel


/**
 * HomeScreen.kt
 * * Pantalla principal de la aplicación que muestra el listado de Pokémon con paginación.
 */
@Composable
fun HomeScreen(
    goToDetail: (Long?) -> Unit
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val query by viewModel.searchQuery.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    val listState = rememberLazyGridState()
    val pagedItems = viewModel.pokemonPagingData.collectAsLazyPagingItems()

    LaunchedEffect(uiState.sortBy) {
        listState.scrollToItem(0)
    }

    Scaffold { innerPadding ->
        BodyStructure(
            modifier = Modifier.padding(innerPadding),
            header = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextFieldComponent(
                        modifier = Modifier.weight(0.85f),
                        value = query,
                        icon = Icons.Default.Search,
                        placeholder = "Search",
                        onValueChange = viewModel::onQueryChanged,
                        hideCondition = true
                    )

                    Icon(
                        modifier = Modifier
                            .weight(0.15f)
                            .clickable {
                                viewModel.showDialog(true)
                            },
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null
                    )

                    if(uiState.showDialog) {
                        SortByDialog(
                            selectedOption = uiState.sortBy,
                            onOptionSelected = viewModel::sortItemsBy
                        ) {
                            viewModel.showDialog(false)
                        }
                    }
                }
            }) {
            if (uiState.isLoading) {
                LoadingLazyGrid()
            } else {
                if (uiState.count > 0) {
                    LazyGridWithPagination(
                        pagedItems = pagedItems,
                        query = query,
                        listState = listState,
                        markAsFavorite = viewModel::markAsFavorite,
                        goToDetail = goToDetail
                    )
                } else {
                    EmptyState(
                        title = stringResource(R.string.home_empty_dex_title),
                        description = stringResource(R.string.home_empty_dex_message),
                        onRetry = viewModel::loadData
                    )
                }
            }
        }
    }
}

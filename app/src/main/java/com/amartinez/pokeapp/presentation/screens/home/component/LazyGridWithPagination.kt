package com.amartinez.pokeapp.presentation.screens.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.amartinez.pokeapp.R
import com.amartinez.pokeapp.domain.model.Pokemon
import com.amartinez.pokeapp.presentation.components.EmptyState

@Composable
fun LazyGridWithPagination(
    pagedItems: LazyPagingItems<Pokemon>,
    query: String,
    listState: LazyGridState,
    markAsFavorite: (Long, Boolean) -> Unit,
    goToDetail: (Long?) -> Unit
) {
    val isFinishedAndEmpty =
        pagedItems.loadState.refresh is LoadState.NotLoading && pagedItems.itemCount == 0

    if (isFinishedAndEmpty) {
        EmptyState(
            title = stringResource(R.string.home_empty_search_title),
            description = stringResource(R.string.home_empty_search_message, query)
        )
    } else {
        LazyVerticalGrid(
            state = listState,
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxWidth(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                count = pagedItems.itemCount,
                key = pagedItems.itemKey { it.name },
                contentType = { "pokemon_card" }
            ) { index ->
                val pokemon = pagedItems[index]
                if (pokemon != null) {
                    PokemonCardItem(
                        pokemon = pokemon,
                        goToDetail = goToDetail,
                        markAsFavorite = { id ->
                            markAsFavorite(id, pokemon.isFavorite)
                        }
                    )
                } else {
                    PokemonShimmerItem()
                }
            }

        }
    }
}
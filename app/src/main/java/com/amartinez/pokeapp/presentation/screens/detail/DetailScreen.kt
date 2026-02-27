package com.amartinez.pokeapp.presentation.screens.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.amartinez.pokeapp.R
import com.amartinez.pokeapp.presentation.components.BodyStructure
import com.amartinez.pokeapp.presentation.components.EmptyState
import com.amartinez.pokeapp.presentation.screens.detail.components.DetailShimmer
import com.amartinez.pokeapp.presentation.screens.detail.components.DetailView
import com.amartinez.pokeapp.presentation.viewmodel.detail.DetailViewModel

/**
 * DetailScreen.kt
 * * Pantalla de detalle que muestra la información de un Pokémon específico.
 */
@Composable
fun DetailScreen(
    id: Long,
    onBackPressed: () -> Unit
) {
    val viewModel: DetailViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.getPokemonById(id)
    }

    val uiState by viewModel.uiState.collectAsState()
    val pokemon = uiState.pokemon

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            if (!uiState.isLoading) {
                if (!uiState.error) {
                    DetailView(
                        pokemon = pokemon,
                        markAsFavorite = viewModel::markAsFavorite,
                        onBackPressed = onBackPressed
                    )
                } else {
                    BodyStructure {
                        EmptyState(
                            title = stringResource(R.string.detail_error_title),
                            description = stringResource(R.string.detail_error_message),
                            onRetry = {
                                viewModel.getPokemonById(id)
                            }
                        )
                    }
                }
            } else {
                DetailShimmer()
            }
        }
    }
}
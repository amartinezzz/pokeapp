package com.amartinez.pokeapp.presentation.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.amartinez.pokeapp.R
import com.amartinez.pokeapp.presentation.components.InformationComponent
import com.amartinez.pokeapp.presentation.components.StatComponent
import com.amartinez.pokeapp.presentation.components.TypeComponent
import com.amartinez.pokeapp.presentation.utils.capitalizedFirstChar
import com.amartinez.pokeapp.presentation.utils.formatId
import com.amartinez.pokeapp.presentation.utils.toTypeColor
import com.amartinez.pokeapp.presentation.viewmodel.detail.DetailViewModel

@Composable
fun DetailScreen(
    id: Long,
    onBackPressed: () -> Unit,
) {
    val viewModel: DetailViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.getPokemonById(id)
    }

    val uiState by viewModel.uiState.collectAsState()
    val pokemon = uiState.pokemon

    if (!uiState.isLoading) {
        val typeColor =
            if (pokemon.types.isEmpty() == false) pokemon.types[0].type.name.toTypeColor() else MaterialTheme.colorScheme.surfaceVariant

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(typeColor)
        ) {
            val (bgImageRef,
                headerRef,
                imageRef,
                cardRef) = createRefs()

            Image(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .size(205.dp)
                    .constrainAs(bgImageRef) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    },
                contentScale = ContentScale.Fit,
                painter = painterResource(R.drawable.pokeball_bg),
                contentDescription = null
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(76.dp)
                    .constrainAs(headerRef) {
                        top.linkTo(parent.top)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .padding(start = 25.dp)
                        .weight(1f)
                        .clickable {
                            onBackPressed()
                        },
                    painter = painterResource(R.drawable.arrow_back),
                    contentDescription = "Back"
                )
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(4f),
                    text = pokemon.name.capitalizedFirstChar(),
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    modifier = Modifier.padding(end = 24.dp),
                    text = pokemon.id.formatId(),
                    style = MaterialTheme.typography.labelMedium
                )
            }

            Card(
                modifier = Modifier
                    .padding(top = 180.dp, start = 4.dp, end = 4.dp, bottom = 4.dp)
                    .fillMaxWidth()
                    .constrainAs(cardRef) {
                        top.linkTo(headerRef.bottom)
                        bottom.linkTo(parent.bottom)
                    },
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp, end = 20.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Icon(
                            modifier = Modifier
                                .clickable {
                                    viewModel.markAsFavorite(pokemon.id, pokemon.isFavorite)
                                },
                            imageVector = if (pokemon.isFavorite == true) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            tint = if (pokemon.isFavorite == true) MaterialTheme.colorScheme.primary else LocalContentColor.current,
                            contentDescription = null
                        )
                    }

                    Spacer(modifier = Modifier.height(56.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        pokemon.types.forEach { type ->
                            TypeComponent(type)
                        }
                    }

                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = stringResource(R.string.detail_about_title),
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = typeColor
                        )
                    )

                    Row(
                        modifier = Modifier
                            .height(80.dp)
                            .padding(horizontal = 20.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        InformationComponent(
                            modifier = Modifier.weight(1f),
                            icon = R.drawable.weight_icon,
                            values = listOf(pokemon.weight.toString()),
                            unit = stringResource(R.string.weight_unit),
                            feature = stringResource(R.string.detail_weight)
                        )

                        VerticalDivider(
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                                .fillMaxHeight(),
                            thickness = 1.dp,
                            color = Color.LightGray.copy(alpha = 0.5f)
                        )

                        InformationComponent(
                            modifier = Modifier.weight(1f),
                            icon = R.drawable.height_icon,
                            values = listOf(pokemon.height.toString()),
                            unit = stringResource(R.string.height_unit),
                            feature = stringResource(R.string.detail_height)
                        )

                        VerticalDivider(
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                                .fillMaxHeight(),
                            thickness = 1.dp,
                            color = Color.LightGray.copy(alpha = 0.5f)
                        )

                        InformationComponent(
                            modifier = Modifier.weight(1f),
                            values = pokemon.abilities.map { ability ->
                                ability.ability?.name ?: "-"
                            }.toList(),
                            feature = stringResource(R.string.detail_abilities)
                        )
                    }

                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = stringResource(R.string.detail_stats_title),
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = typeColor
                        )
                    )

                    pokemon.stats.forEach { stat ->
                        StatComponent(stat, typeColor)
                    }
                }
            }

            AsyncImage(
                modifier = Modifier
                    .size(200.dp)
                    .constrainAs(imageRef) {
                        top.linkTo(headerRef.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                model = pokemon.imageUrl,
                contentDescription = "Description",
                //placeholder = painterResource(R.drawable.db_radar),
                contentScale = ContentScale.Fit,
            )
        }
    }
}
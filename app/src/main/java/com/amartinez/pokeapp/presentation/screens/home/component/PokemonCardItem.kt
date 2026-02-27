package com.amartinez.pokeapp.presentation.screens.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.amartinez.pokeapp.R
import com.amartinez.pokeapp.domain.model.Pokemon
import com.amartinez.pokeapp.presentation.utils.capitalizedFirstChar
import com.amartinez.pokeapp.presentation.utils.formatId

@Composable
fun PokemonCardItem(
    pokemon: Pokemon?,
    goToDetail: (Long?) -> Unit,
    markAsFavorite: (Long) -> Unit
) {
    Card(
        modifier = Modifier
            .background(color = Color.White)
            .clickable {
                goToDetail(pokemon?.id)
            },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        ConstraintLayout(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
        ) {
            val (favoriteRef,
                numberDexRef,
                imageRef,
                nameRef) = createRefs()


            Icon(
                modifier = Modifier
                    .constrainAs(favoriteRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .clickable {
                        markAsFavorite(pokemon?.id ?: 0)
                    },
                imageVector = if (pokemon?.isFavorite == true) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                tint = if (pokemon?.isFavorite == true) MaterialTheme.colorScheme.primary else LocalContentColor.current,
                contentDescription = "Favorite"
            )


            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(imageRef) {
                        top.linkTo(numberDexRef.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemon?.imageUrl)
                    .crossfade(true)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = pokemon?.name?.capitalizedFirstChar(),
                placeholder = painterResource(R.drawable.pokeball),
                contentScale = ContentScale.Fit,
                alignment = Alignment.TopStart
            )

            Text(
                modifier = Modifier
                    .padding(top = 4.dp, end = 8.dp)
                    .constrainAs(numberDexRef) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    },
                text = pokemon?.id?.formatId() ?: "",
                style = MaterialTheme.typography.labelSmall
            )

            Text(
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .constrainAs(nameRef) {
                        top.linkTo(imageRef.bottom)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        verticalBias = 1.0f
                    },
                text = pokemon?.name?.capitalizedFirstChar() ?: "",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
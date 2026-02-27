package com.amartinez.pokeapp.presentation.screens.home.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.amartinez.pokeapp.presentation.utils.shimmerEffect

@Composable
fun PokemonShimmerItem() {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            val (favoriteRef,
                numberDexRef,
                imageRef,
                nameRef) = createRefs()

            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .shimmerEffect()
                    .constrainAs(favoriteRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            )

            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(12.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
                    .constrainAs(numberDexRef) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .shimmerEffect()
                    .constrainAs(imageRef) {
                        top.linkTo(numberDexRef.bottom, margin = 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(12.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
                    .constrainAs(nameRef) {
                        top.linkTo(imageRef.bottom, margin = 12.dp)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        }
    }
}
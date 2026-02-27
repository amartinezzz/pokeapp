package com.amartinez.pokeapp.presentation.screens.detail.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.amartinez.pokeapp.presentation.utils.shimmerEffect

@Composable
fun DetailShimmer() {
    val bgColor = Color.LightGray.copy(alpha = 0.3f)

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
    ) {
        val (headerRef,
            imageRef,
            cardRef) = createRefs()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(76.dp)
                .padding(horizontal = 25.dp)
                .constrainAs(headerRef) { top.linkTo(parent.top) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier
                .size(24.dp)
                .shimmerEffect())
            Spacer(modifier = Modifier.width(16.dp))
            Box(modifier = Modifier
                .width(150.dp)
                .height(24.dp)
                .shimmerEffect())
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
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(56.dp))

                Row {
                    Box(modifier = Modifier
                        .size(80.dp, 25.dp)
                        .clip(CircleShape)
                        .shimmerEffect())
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(modifier = Modifier
                        .size(80.dp, 25.dp)
                        .clip(CircleShape)
                        .shimmerEffect())
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)) {
                    Box(modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .shimmerEffect())
                    Spacer(modifier = Modifier.width(20.dp))
                    Box(modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .shimmerEffect())
                    Spacer(modifier = Modifier.width(20.dp))
                    Box(modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .shimmerEffect())
                }

                Spacer(modifier = Modifier.height(32.dp))

                repeat(5) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(15.dp)
                            .padding(vertical = 4.dp)
                            .shimmerEffect()
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .shimmerEffect()
                .constrainAs(imageRef) {
                    top.linkTo(headerRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }
}
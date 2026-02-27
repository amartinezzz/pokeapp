package com.amartinez.pokeapp.presentation.screens.detail.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun InformationComponent(
    modifier: Modifier,
    @DrawableRes icon: Int? = null,
    values: List<String>,
    unit: String? = null,
    feature: String
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (iconRef,
            valuesRef,
            featureRef) = createRefs()

        if (icon != null) {
            Image(
                modifier = Modifier
                    .padding(top = 10.dp, start = 20.dp)
                    .constrainAs(iconRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    },
                painter = painterResource(icon),
                contentDescription = null
            )
        }

        Column(
            modifier = Modifier
                .padding(top = if(icon != null) 10.dp else 0.dp, start = 8.dp)
                .constrainAs(valuesRef) {
                    if (icon != null) {
                        top.linkTo(iconRef.top)
                        start.linkTo(iconRef.end)
                    } else {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                }
        ) {
            values.forEach { value ->
                Text(
                    text = "$value ${unit ?: ""}",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }

        Text(
            modifier = Modifier
                .padding(top = 4.dp)
                .constrainAs(featureRef) {
                    top.linkTo(valuesRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            text = feature,
            style = MaterialTheme.typography.labelSmall.copy(
                lineHeight = 8.sp,
                fontSize = 8.sp
            )
        )
    }
}
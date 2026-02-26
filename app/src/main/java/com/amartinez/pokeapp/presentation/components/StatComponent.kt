package com.amartinez.pokeapp.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.amartinez.pokeapp.domain.model.Stat
import com.amartinez.pokeapp.presentation.utils.formatNumber

@Composable
fun StatComponent(
    stat: Stat,
    typeColor: Color
) {
    ConstraintLayout(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .height(16.dp)
    ) {
        val (nameRef,
            dividerRef,
            statRef,
            progressRef) = createRefs()

        Box(
            modifier = Modifier
            .width(27.dp)
            .constrainAs(nameRef) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
            },
            contentAlignment = Alignment.CenterEnd) {
            Text(
                text = shortName(stat.stat.name),
                style = MaterialTheme.typography.labelSmall.copy(
                    color = typeColor
                )
            )
        }

        VerticalDivider(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxHeight()
                .constrainAs(dividerRef) {
                    top.linkTo(parent.top)
                    start.linkTo(nameRef.end)
                    bottom.linkTo(parent.bottom)
                },
            thickness = 1.dp,
            color = Color.LightGray.copy(alpha = 0.5f)
        )

        Text(
            modifier = Modifier
                .constrainAs(statRef) {
                    top.linkTo(parent.top)
                    start.linkTo(dividerRef.end)
                    bottom.linkTo(parent.bottom)
                },
            text = stat.baseStat.formatNumber(),
            style = MaterialTheme.typography.labelSmall
        )

        LinearProgressIndicator(
            modifier = Modifier
                .height(8.dp)
                .clip(CircleShape)
                .constrainAs(progressRef) {
                    top.linkTo(parent.top)
                    start.linkTo(statRef.end)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            progress = { calculateStatProgress(stat.baseStat) },
            color = typeColor,
            trackColor = typeColor.copy(alpha = 0.2f)
        )
    }
}

private fun calculateStatProgress(baseStat: Long): Float {
    return try {
        baseStat / 255f
    } catch (_: Exception) {
        0f
    }
}

private fun shortName(name: String): String {
    return when(name) {
        "attack" -> "ATK"
        "defense" -> "DEF"
        "special-attack" -> "SATK"
        "special-defense" -> "SDEF"
        "speed" -> "SPD"
        else -> "HP"
    }
}
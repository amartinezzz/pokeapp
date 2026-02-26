package com.amartinez.pokeapp.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amartinez.pokeapp.domain.model.Type
import com.amartinez.pokeapp.presentation.utils.capitalizedFirstChar
import com.amartinez.pokeapp.presentation.utils.toTypeColor

@Composable
fun TypeComponent(
    type: Type,
) {
    val name = type.type.name

    Surface(
        modifier = Modifier.padding(end = 16.dp).clip(RoundedCornerShape(32.dp)),
        color = name.toTypeColor(),
    ) {
        Text(
            modifier = Modifier.padding(4.dp),
            text = name.capitalizedFirstChar(),
            style = MaterialTheme.typography.labelMedium.copy(
                fontSize = 10.sp
            )
        )
    }
}
package com.amartinez.pokeapp.presentation.screens.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.amartinez.pokeapp.R
import com.amartinez.pokeapp.domain.model.SortOption

@Composable
fun SortByDialog(
    selectedOption: SortOption,
    onOptionSelected: (SortOption) -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .width(180.dp)
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Sort by:",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(bottom = 12.dp, start = 4.dp)
                )

                Card(
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        SortOptionRow(
                            label = stringResource(R.string.sort_by_number),
                            icon = Icons.Default.KeyboardArrowUp,
                            selected = selectedOption == SortOption.NUMBER_ASC,
                            onClick = { onOptionSelected(SortOption.NUMBER_ASC) }
                        )
                        SortOptionRow(
                            label = stringResource(R.string.sort_by_number),
                            icon = Icons.Default.KeyboardArrowDown,
                            selected = selectedOption == SortOption.NUMBER_DESC,
                            onClick = { onOptionSelected(SortOption.NUMBER_DESC) }
                        )
                        SortOptionRow(
                            label = stringResource(R.string.sort_by_name),
                            icon = Icons.Default.KeyboardArrowUp,
                            selected = selectedOption == SortOption.NAME_ASC,
                            onClick = { onOptionSelected(SortOption.NAME_ASC) }
                        )
                        SortOptionRow(
                            label = stringResource(R.string.sort_by_name),
                            icon = Icons.Default.KeyboardArrowDown,
                            selected = selectedOption == SortOption.NAME_DESC,
                            onClick = { onOptionSelected(SortOption.NAME_DESC) }
                        )
                        SortOptionRow(
                            label = stringResource(R.string.sort_by_favorite),
                            icon = Icons.Default.KeyboardArrowUp,
                            selected = selectedOption == SortOption.FAVORITE_ASC,
                            onClick = { onOptionSelected(SortOption.FAVORITE_ASC) }
                        )
                        SortOptionRow(
                            label = stringResource(R.string.sort_by_favorite),
                            icon = Icons.Default.KeyboardArrowDown,
                            selected = selectedOption == SortOption.FAVORITE_DESC,
                            onClick = { onOptionSelected(SortOption.FAVORITE_DESC) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SortOptionRow(
    label: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            modifier = Modifier.weight(0.15f),
            selected = selected,
            onClick = null,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.primary,
                unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            modifier = Modifier.weight(0.70f),
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )

        Icon(
            modifier = Modifier.weight(0.15f),
            imageVector = icon,
            contentDescription = null
        )
    }
}
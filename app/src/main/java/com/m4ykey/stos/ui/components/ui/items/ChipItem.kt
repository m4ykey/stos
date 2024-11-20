package com.m4ykey.stos.ui.components.ui.items

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChipItem(
    modifier : Modifier = Modifier,
    title: String,
    selected: Boolean,
    onSelected: (Boolean) -> Unit
) {
    FilterChip(
        modifier = modifier.wrapContentWidth(),
        selected = selected,
        onClick = { onSelected(!selected) },
        label = { Text(text = title) },
        leadingIcon = {
            if (selected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    )
}
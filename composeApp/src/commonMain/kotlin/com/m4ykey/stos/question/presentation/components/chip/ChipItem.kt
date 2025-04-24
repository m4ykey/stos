package com.m4ykey.stos.question.presentation.components.chip

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
    title : String,
    selected : Boolean,
    onSelect : (Boolean) -> Unit
) {
    FilterChip(
        modifier = modifier.wrapContentWidth(),
        selected = selected,
        onClick = { onSelect(!selected) },
        label = { Text(text = title) },
        leadingIcon = {
            if (selected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = title,
                    modifier = modifier.size(14.dp)
                )
            }
        }
    )
}
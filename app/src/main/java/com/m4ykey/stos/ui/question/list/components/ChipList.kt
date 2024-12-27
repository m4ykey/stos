package com.m4ykey.stos.ui.question.list.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.m4ykey.stos.ui.question.list.QuestionSort

@Composable
fun ChipItem(
    modifier: Modifier = Modifier,
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
                    contentDescription = title,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    )
}

@Composable
fun ChipList(
    modifier: Modifier = Modifier,
    onChipSelected : (QuestionSort) -> Unit,
    selectedChip : QuestionSort
) {
    val chipList = QuestionSort.entries.map { sort ->
        stringResource(id = sort.displayNameResId) to sort
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
    ) {
        items(chipList) { (label, sortKey) ->
            ChipItem(
                title = label,
                selected = selectedChip == sortKey,
                onSelected = { onChipSelected(sortKey) },
                modifier = modifier.padding(horizontal = 5.dp)
            )
        }
    }
}
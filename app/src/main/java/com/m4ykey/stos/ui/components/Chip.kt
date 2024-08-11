package com.m4ykey.stos.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.m4ykey.stos.R


@Composable
fun ChipList(
    modifier: Modifier = Modifier,
    onSortSelected : (String) -> Unit,
    selectedSort : String
) {

    val chipList = listOf(
        stringResource(id = R.string.hot) to "hot",
        stringResource(id = R.string.activity) to "activity",
        stringResource(id = R.string.votes) to "votes",
        stringResource(id = R.string.creation) to "creation",
        stringResource(id = R.string.week) to "week",
        stringResource(id = R.string.month) to "month"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .horizontalScroll(rememberScrollState())
    ) {
        chipList.forEach { (label, sortKey) ->
            Chip(
                modifier = modifier.padding(horizontal = 5.dp),
                title = label,
                selected = selectedSort == sortKey,
                onSelected = { onSortSelected(sortKey) }
            )
        }
    }
}

@Composable
fun Chip(
    modifier : Modifier = Modifier,
    title: String,
    selected: Boolean,
    onSelected: (Boolean) -> Unit,
) {
    FilterChip(
        modifier = modifier,
        selected = selected,
        onClick = { onSelected(selected) },
        label = { Text(text = title) },
        leadingIcon = {
            if (selected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = modifier
                        .height(14.dp)
                        .width(14.dp)
                )
            }
        }
    )
}
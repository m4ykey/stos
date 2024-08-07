package com.m4ykey.stos

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.m4ykey.stos.ui.question.QuestionList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onQuestionClick : (Int) -> Unit
) {

    var sortType by remember { mutableStateOf("hot") }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { },
                actions = {
                    IconButton(onClick = {  }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null
                        )
                    }
                    IconButton(onClick = {  }) {
                        Icon(
                            imageVector = Icons.Outlined.AccountCircle,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Spacer(modifier = modifier.height(5.dp))
            ChipList(
                selectedSort = sortType,
                onSortSelected = { sortType = it }
            )
            Spacer(modifier = modifier.height(5.dp))
            QuestionList(
                sort = sortType,
                onQuestionClick = onQuestionClick
            )
        }
    }
}

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
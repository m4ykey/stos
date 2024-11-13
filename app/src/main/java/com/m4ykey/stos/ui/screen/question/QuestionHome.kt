package com.m4ykey.stos.ui.screen.question

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.m4ykey.stos.R
import com.m4ykey.stos.ui.components.ui.ChipItem
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionHome(
    modifier: Modifier = Modifier,
    onQuestionClick : (Int) -> Unit,
    onSearchClick : () -> Unit,
    onUserClick : () -> Unit,
    onOwnerClick : (Int) -> Unit,
    viewModel: QuestionViewModel = koinViewModel()
) {

    var sortType by remember { mutableStateOf("hot") }

    LaunchedEffect(key1 = sortType) {
        if (viewModel.shouldLoadData(sortType)) {
            viewModel.getQuestions(sort = sortType)
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { },
                actions = {
                    IconButton(onClick = onSearchClick) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(id = R.string.search)
                        )
                    }
                    IconButton(onClick = onUserClick) {
                        Icon(
                            imageVector = Icons.Outlined.AccountCircle,
                            contentDescription = stringResource(id = R.string.user_profile)
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
                modifier = modifier.padding(10.dp),
                onQuestionClick = onQuestionClick,
                onOwnerClick = onOwnerClick
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

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
    ) {
        items(chipList) { (label, sortKey) ->
            ChipItem(
                modifier = modifier.padding(horizontal = 5.dp),
                title = label,
                selected = selectedSort == sortKey,
                onSelected = { onSortSelected(sortKey) }
            )
        }
    }
}
package com.m4ykey.stos.question.presentation.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.question.presentation.QuestionViewModel
import com.m4ykey.stos.question.presentation.components.QuestionItem
import com.m4ykey.stos.question.presentation.components.chip.ChipList
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionListScreen(
    viewModel: QuestionViewModel = koinViewModel(),
    onQuestionClick : (Int) -> Unit,
    onSearchClick : () -> Unit,
    onOwnerClick : (Int) -> Unit
) {
    val state by viewModel.qListState.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val listState = rememberLazyListState()

    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            lastVisible != null && lastVisible >= state.questions.size - 3
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { shouldLoadMore.value }
            .collect { shouldLoad ->
                if (shouldLoad) viewModel.loadNextPage()
            }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = {},
                actions = {
                    IconButton(onClick = onSearchClick) {
                        Icon(
                            contentDescription = null,
                            imageVector = Icons.Default.Search
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                state.errorMessage != null -> {
                    Text(
                        text = state.errorMessage.orEmpty(),
                        color = Color.Red,
                        fontSize = 16.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                state.questions.isNotEmpty() -> {
                    QuestionListContent(
                        padding = padding,
                        listState = listState,
                        onOwnerClick = onOwnerClick,
                        onQuestionClick = onQuestionClick,
                        sort = state.sort,
                        questions = state.questions,
                        onAction = { action -> viewModel.onAction(action) }
                    )
                }
            }
        }
    }
}

@Composable
fun QuestionListContent(
    padding : PaddingValues,
    listState : LazyListState,
    onOwnerClick : (Int) -> Unit,
    onQuestionClick : (Int) -> Unit,
    sort: QuestionSort,
    onAction : (QuestionListAction) -> Unit,
    questions : List<Question>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        ChipList(
            selectedChip = sort,
            onChipSelected = { selectedSort ->
                onAction(QuestionListAction.OnSortClick(selectedSort))
            }
        )
        Spacer(modifier = Modifier.height(5.dp))
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(10.dp)
        ) {
            items(
                items = questions,
                key = { it.questionId }
            ) { questions ->
                QuestionItem(
                    question = questions,
                    onQuestionClick = onQuestionClick,
                    onOwnerClick = onOwnerClick
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 5.dp)
                )
            }
        }
    }
}
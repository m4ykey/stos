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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.question.presentation.components.ErrorComponent
import com.m4ykey.stos.question.presentation.components.chip.ChipList
import com.m4ykey.stos.question.presentation.components.list_items.QuestionItem
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import stos.composeapp.generated.resources.Res
import stos.composeapp.generated.resources.search

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionListScreen(
    viewModel: QuestionListViewModel = koinViewModel(),
    onQuestionClick : (Int) -> Unit,
    onSearchClick : () -> Unit,
    onOwnerClick : (Int) -> Unit
) {

    val questions = viewModel.getQuestionsFlow().collectAsLazyPagingItems()

    LaunchedEffect(viewModel) {
        viewModel.getQuestionsFlow()
    }

    val viewState by viewModel.qListState.collectAsState()
    val sort = viewState.sort
    val isLoading = viewState.isLoading
    val errorMessage = viewState.errorMessage

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val listState = rememberSaveable(saver = LazyListState.Saver) {
        LazyListState()
    }

    LaunchedEffect(viewModel) {
        viewModel.listUiEvent.collect { event ->
            when (event) {
                is ListUiEvent.NavigateToUser -> onOwnerClick(event.userId)
                is ListUiEvent.NavigateToQuestion -> onQuestionClick(event.questionId)
                is ListUiEvent.ChangeSort -> viewModel.updateSort(event.sort)
                else -> null
            }
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
                            contentDescription = stringResource(resource = Res.string.search),
                            imageVector = Icons.Default.Search
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                errorMessage != null -> {
                    ErrorComponent(errorMessage)
                }
                questions.itemCount > 0 -> {
                    QuestionListContent(
                        padding = padding,
                        listState = listState,
                        sort = sort,
                        questions = questions,
                        onAction = viewModel::onAction
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
    sort: QuestionSort,
    onAction : (QuestionListAction) -> Unit,
    questions : LazyPagingItems<Question>
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
                count = questions.itemCount,
                key = { index -> questions[index]?.questionId ?: index },
                contentType = { "question_item" }
            ) { index ->
                questions[index]?.let { question ->
                    QuestionItem(
                        question = question,
                        onQuestionClick = {
                            onAction(QuestionListAction.OnQuestionClick(question.questionId))
                        },
                        onOwnerClick = {
                            onAction(QuestionListAction.OnOwnerClick(question.owner.userId))
                        }
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 5.dp)
                    )
                }
            }
        }
    }
}
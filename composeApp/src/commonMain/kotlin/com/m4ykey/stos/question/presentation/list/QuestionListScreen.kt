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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.question.presentation.components.ErrorComponent
import com.m4ykey.stos.question.presentation.components.QuestionItem
import com.m4ykey.stos.question.presentation.components.chip.ChipList
import kotlinx.coroutines.flow.distinctUntilChanged
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

    LaunchedEffect(Unit) {
        if (viewModel.qListState.value.questions.isEmpty()) {
            viewModel.observeSortingChangesForHome()
        }
    }

    val state by viewModel.qListState.collectAsState()
    val question = state.questions
    val sort = state.sort
    val isLoading = state.isLoading
    val errorMessage = state.errorMessage

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val listState = rememberSaveable(saver = LazyListState.Saver) {
        LazyListState()
    }

    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            lastVisible != null && lastVisible >= question.size - 3
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.listUiEvent.collect { event ->
            when (event) {
                is ListUiEvent.NavigateToUser -> onOwnerClick(event.userId)
                is ListUiEvent.NavigateToQuestion -> onQuestionClick(event.questionId)
                is ListUiEvent.ChangeSort -> viewModel.updateSort(event.sort)
            }
        }
    }


    LaunchedEffect(listState) {
        snapshotFlow { shouldLoadMore.value }
            .distinctUntilChanged()
            .collect { shouldLoad ->
                if (shouldLoad) viewModel.loadNextPageForHome()
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
                question.isNotEmpty() -> {
                    QuestionListContent(
                        padding = padding,
                        listState = listState,
                        sort = sort,
                        questions = question,
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
                key = { it.questionId },
                contentType = { "question_item" }
            ) { question ->
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
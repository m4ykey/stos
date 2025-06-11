package com.m4ykey.stos.question.presentation.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import com.m4ykey.stos.core.paging.BasePagingList
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.question.presentation.components.chip.ChipList
import com.m4ykey.stos.question.presentation.components.list_items.QuestionItem
import kotlinx.coroutines.flow.collectLatest
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
    val viewState by viewModel.qListState.collectAsState()
    val sort by rememberUpdatedState(viewState.sort)

    val currentOnAction by rememberUpdatedState(newValue = viewModel::onAction)

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val listState = rememberSaveable(saver = LazyListState.Saver) {
        LazyListState()
    }

    LaunchedEffect(Unit) {
        viewModel.listUiEvent.collectLatest { event ->
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
        QuestionListContent(
            padding = padding,
            listState = listState,
            sort = sort,
            questions = questions,
            onAction = currentOnAction
        )
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
            .padding(padding)
            .fillMaxSize()
    ) {
        ChipList(
            selectedChip = sort,
            onChipSelected = { selectedSort ->
                onAction(QuestionListAction.OnSortClick(selectedSort))
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        BasePagingList(
            modifier = Modifier.fillMaxWidth(),
            listState = listState,
            items = questions,
            itemContent = { question ->
                QuestionItem(
                    question = question,
                    onQuestionClick = {
                        onAction(QuestionListAction.OnQuestionClick(question.questionId))
                    },
                    onOwnerClick = {
                        onAction(QuestionListAction.OnOwnerClick(question.owner.userId))
                    }
                )
            }
        )
    }
}
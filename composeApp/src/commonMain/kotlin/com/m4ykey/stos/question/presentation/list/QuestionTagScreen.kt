package com.m4ykey.stos.question.presentation.list

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import app.cash.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import stos.composeapp.generated.resources.Res
import stos.composeapp.generated.resources.back

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionTagScreen(
    tag : String,
    viewModel: QuestionListViewModel = koinViewModel(),
    onNavBack : () -> Unit,
    onQuestionClick : (Int) -> Unit,
    onOwnerClick : (Int) -> Unit
) {

    val questions = viewModel.getQuestionsTagFlow(tag).collectAsLazyPagingItems()
    val viewState by viewModel.qListState.collectAsState()
    val sort by rememberUpdatedState(viewState.sort)

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
                title = {
                    Text(text = tag)
                },
                navigationIcon = {
                    IconButton(onClick = onNavBack) {
                        Icon(
                            contentDescription = stringResource(resource = Res.string.back),
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack
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
            onAction = viewModel::onAction
        )
    }
}
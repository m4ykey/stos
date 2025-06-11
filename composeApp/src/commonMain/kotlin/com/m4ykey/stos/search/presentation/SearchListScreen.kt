package com.m4ykey.stos.search.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.collectAsLazyPagingItems
import com.m4ykey.stos.question.presentation.list.ListUiEvent
import com.m4ykey.stos.question.presentation.list.QuestionListAction
import com.m4ykey.stos.question.presentation.list.QuestionListContent
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import stos.composeapp.generated.resources.Res
import stos.composeapp.generated.resources.back

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchListScreen(
    tag : String,
    inTitle : String,
    onNavBack : () -> Unit,
    viewModel : SearchViewModel = koinViewModel(),
    onQuestionClick : (Int) -> Unit,
    onOwnerClick : (Int) -> Unit
) {

    val initialSearchText = buildString {
        if (inTitle.isNotEmpty()) {
            append(inTitle)
        }
        if (tag.isNotEmpty()) {
            if (inTitle.isNotEmpty()) append(" ")
            append(tag)
        }
    }
    var searchText by rememberSaveable { mutableStateOf(initialSearchText) }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val listState = rememberSaveable(saver = LazyListState.Saver) {
        LazyListState()
    }

    val viewState by viewModel.qListState.collectAsState()
    val sort by rememberUpdatedState(viewState.sort)

    LaunchedEffect(inTitle, tag) {
        if (initialSearchText.isNotEmpty()) {
            viewModel.searchQuestion(inTitle, tag)
        }
    }

    val searchResult = viewModel
        .searchFlow
        .collectAsLazyPagingItems()

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
                title = {  },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            SearchBox(
                value = searchText,
                onSearch = {
                    viewModel.searchQuestion(searchText, tag)
                },
                onValueChange = { searchText = it },
                modifier = Modifier.padding(horizontal = 32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            QuestionListContent(
                sort = sort,
                questions = searchResult,
                padding = PaddingValues(8.dp),
                listState = listState,
                onAction = { questionAction ->
                    when (questionAction) {
                        is QuestionListAction.OnQuestionClick ->
                            viewModel.onAction(SearchListAction.OnQuestionClick(questionAction.questionId))
                        is QuestionListAction.OnOwnerClick ->
                            viewModel.onAction(SearchListAction.OnOwnerClick(questionAction.userId))
                        is QuestionListAction.OnSortClick ->
                            viewModel.onAction(SearchListAction.OnSortClick(questionAction.sort))
                    }
                }
            )
        }
    }
}
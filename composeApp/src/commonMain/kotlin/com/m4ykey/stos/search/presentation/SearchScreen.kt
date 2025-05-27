package com.m4ykey.stos.search.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.question.presentation.components.ErrorComponent
import com.m4ykey.stos.question.presentation.components.list_items.QuestionItem
import com.m4ykey.stos.question.presentation.detail.TagListWrap
import com.m4ykey.stos.question.presentation.list.ListUiEvent
import kotlinx.coroutines.flow.distinctUntilChanged
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import stos.composeapp.generated.resources.Res
import stos.composeapp.generated.resources.back
import stos.composeapp.generated.resources.search

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onNavBack : () -> Unit,
    viewModel: SearchViewModel = koinViewModel(),
    onOwnerClick : (Int) -> Unit,
    onQuestionClick : (Int) -> Unit
) {

    var inTitle by remember { mutableStateOf("") }
    var tag by remember { mutableStateOf("") }

    val state by viewModel.qListState.collectAsState()
    val question = state.questions
    val isLoading = state.isLoading
    val errorMessage = state.errorMessage

    val listState = rememberSaveable(saver = LazyListState.Saver) {
        LazyListState()
    }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

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
                else -> null
            }
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { shouldLoadMore.value }
            .distinctUntilChanged()
            .collect { shouldLoad ->
                if (shouldLoad) viewModel.search(inTitle, tag)
            }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = { Text(text = stringResource(resource = Res.string.search)) },
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
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                errorMessage != null -> {
                    ErrorComponent(errorMessage)
                }
                question.isNotEmpty() -> {
                    SearchContent(
                        padding = padding,
                        listState = listState,
                        onAction = viewModel::onAction,
                        questions = question
                    )
                }
            }
        }
    }
}

@Composable
fun SearchContent(
    padding : PaddingValues,
    listState : LazyListState,
    questions : List<Question>,
    onAction : (SearchListAction) -> Unit
) {
    LazyColumn(
        state = listState,
        modifier = Modifier
            .padding(padding)
            .padding(horizontal = 10.dp)
            .fillMaxSize()
    ) {
        item {
            SearchBox()
        }
        item {
            TagListWrap(
                tags = emptyList(),
                onTagClick = {  }
            )
        }
        items(
            items = questions,
            key = { it.questionId },
            contentType = { "question_item" }
        ) { question ->
            QuestionItem(
                question = question,
                onOwnerClick = {
                    onAction(SearchListAction.OnOwnerClick(question.owner.userId))
                },
                onQuestionClick = {
                    onAction(SearchListAction.OnQuestionClick(question.questionId))
                }
            )
        }
    }
}

@Composable
fun SearchBox() {
    var text by remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        leadingIcon = {
            Icon(
                contentDescription = null,
                imageVector = Icons.Default.Search
            )
        }
    )
}
package com.m4ykey.stos.question.presentation.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.m4ykey.stos.question.presentation.components.ErrorComponent
import kotlinx.coroutines.flow.distinctUntilChanged
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

    LaunchedEffect(tag) {
        if (viewModel.qListState.value.questions.isEmpty()) {
            //viewModel.loadQuestionsForTag(tag)
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
                else -> null
            }
        }
    }


    LaunchedEffect(listState) {
        snapshotFlow { shouldLoadMore.value }
            .distinctUntilChanged()
            .collect { shouldLoad ->
                //if (shouldLoad) viewModel.loadQuestionsForTag(tag)
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
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                errorMessage != null -> {
                    ErrorComponent(errorMessage)
                }
//                question.isNotEmpty() -> {
//                    QuestionListContent(
//                        padding = padding,
//                        listState = listState,
//                        sort = sort,
//                        questions = question,
//                        onAction = viewModel::onAction
//                    )
//                }
            }
        }
    }
}
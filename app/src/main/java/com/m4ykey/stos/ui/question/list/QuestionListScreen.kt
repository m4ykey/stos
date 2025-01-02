package com.m4ykey.stos.ui.question.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.m4ykey.stos.R
import com.m4ykey.stos.domain.models.questions.Question
import com.m4ykey.stos.ui.question.list.components.ChipList
import com.m4ykey.stos.ui.question.list.components.QuestionList
import org.koin.androidx.compose.koinViewModel

@Composable
fun QuestionListScreen(
    state: QuestionListState,
    onAction: (QuestionListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    state.errorMessage?.let {
        Text(
            text = state.errorMessage.toString(),
            textAlign = TextAlign.Center
        )
    }
    if (state.isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            ChipList(
                selectedChip = state.sort,
                onChipSelected = { selectedSort ->
                    onAction(QuestionListAction.OnSortClick(selectedSort))
                }
            )
            Spacer(modifier = Modifier.height(5.dp))
            QuestionList(
                questions = state.questionResults.collectAsLazyPagingItems(),
                onQuestionClick = { question -> onAction(QuestionListAction.OnQuestionClick(question)) },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionListScreenMain(
    viewModel: QuestionViewModel = koinViewModel(),
    onQuestionClick : (Question) -> Unit,
    onSearchClick : () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = {},
                actions = {
                    IconButton(onClick = { onSearchClick() }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(id = R.string.search)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        QuestionListScreen(
            modifier = Modifier.padding(innerPadding),
            state = state,
            onAction = { action ->
                when (action) {
                    is QuestionListAction.OnQuestionClick -> onQuestionClick(action.question)
                    else -> viewModel.onAction(action)
                }
            }
        )
    }
}
package com.m4ykey.stos.ui.question.list

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.m4ykey.stos.domain.models.questions.Question
import com.m4ykey.stos.ui.question.list.components.QuestionList
import org.koin.androidx.compose.koinViewModel

@Composable
fun QuestionListScreen(
    state : QuestionListState,
    onAction: (QuestionListAction) -> Unit
) {
    Log.i("QuestionListScreen", "isLoading: ${state.isLoading}, errorMessage: ${state.errorMessage}, questionResults: ${state.questionResults}")

    if (state.errorMessage != null) {
        Text(
            text = state.errorMessage.toString(),
            textAlign = TextAlign.Center
        )
    }
    if (state.isLoading) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        QuestionList(
            questions = state.questionResults.collectAsLazyPagingItems(),
            onQuestionClick = {
                onAction(QuestionListAction.OnQuestionClick(it))
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun QuestionListScreenMain(
    viewModel: QuestionViewModel = koinViewModel(),
    onQuestionClick : (Question) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    QuestionListScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is QuestionListAction.OnQuestionClick -> onQuestionClick(action.question)
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}
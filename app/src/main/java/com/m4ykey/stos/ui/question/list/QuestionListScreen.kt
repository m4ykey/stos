package com.m4ykey.stos.ui.question.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

    if (state.isLoading) {
        CircularProgressIndicator()
    } else {
        when {
            state.errorMessage != null -> {
                Text(
                    text = state.errorMessage.toString(),
                    textAlign = TextAlign.Center
                )
            }
            else -> {
                QuestionList(
                    questions = state.questionResults.collectAsLazyPagingItems(),
                    onQuestionClick = {
                        onAction(QuestionListAction.OnQuestionClick(it))
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
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
package com.m4ykey.stos.ui.question

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.m4ykey.network.data.model.Question
import org.koin.androidx.compose.koinViewModel

@Composable
fun QuestionList(
    modifier: Modifier = Modifier,
    viewModel: QuestionViewModel = koinViewModel()
) {
    val uiState by viewModel.questions.collectAsState()
    val questionList : LazyPagingItems<Question> = uiState.questionList.collectAsLazyPagingItems()

    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(
            count = questionList.itemCount,
            key = questionList.itemKey { question -> question.questionId },
            contentType = questionList.itemContentType { "Questions" }
        ) { index ->
            val question = questionList[index]
            if (question != null) {
                QuestionItem(
                    question = question,
                    number = "${index + 1}"
                )
            }
        }
    }
}

@Composable
fun QuestionItem(
    modifier: Modifier = Modifier,
    question: Question,
    number : String
) {
    Row(
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Text(text = number)
        Text(text = question.title)
    }
}
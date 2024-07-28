package com.m4ykey.stos.ui.question

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.m4ykey.network.data.model.Question
import com.m4ykey.stos.markdown.MarkdownText
import com.m4ykey.stos.ui.components.OwnerProfileCard
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuestionList(
    modifier: Modifier = Modifier,
    viewModel: QuestionViewModel = koinViewModel()
) {
    val uiState by viewModel.questions.collectAsState()
    val questionList: LazyPagingItems<Question> = uiState.questionList.collectAsLazyPagingItems()

    CompositionLocalProvider(
        value = LocalOverscrollConfiguration provides null
    ) {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(
                count = questionList.itemCount,
                key = questionList.itemKey { question -> question.questionId },
                contentType = questionList.itemContentType { "Questions" }
            ) { index ->
                val question = questionList[index]
                if (question != null) {
                    QuestionItem(question = question)
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun QuestionItem(
    modifier: Modifier = Modifier,
    question: Question
) {
    Column(
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OwnerProfileCard(
                owner = question.owner,
                size = 32.dp
            )
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                verticalArrangement = Arrangement.Top
            ) {
                MarkdownText(
                    text = question.owner.displayName,
                    fontSize = 14.sp
                )
                Text(
                    text = question.owner.reputation.toString(),
                    fontSize = 13.sp
                )
            }
        }
        MarkdownText(text = question.title)
    }
}
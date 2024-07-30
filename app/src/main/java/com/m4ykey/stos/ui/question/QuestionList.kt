package com.m4ykey.stos.ui.question

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.m4ykey.network.data.model.Question
import com.m4ykey.stos.R
import com.m4ykey.stos.markdown.MarkdownText
import com.m4ykey.stos.ui.components.OwnerProfile
import com.m4ykey.stos.ui.components.QuestionCount
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuestionList(
    modifier: Modifier = Modifier,
    viewModel: QuestionViewModel = koinViewModel(),
    sort : String
) {
    val uiState by viewModel.questions.collectAsState()
    val questionList: LazyPagingItems<Question> = uiState.questionList.collectAsLazyPagingItems()

    LaunchedEffect(sort) {
        viewModel.getQuestions(sort = sort)
    }

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
        OwnerProfile(owner = question.owner)
        Spacer(modifier = modifier.height(5.dp))
        MarkdownText(text = question.title)
        Spacer(modifier = modifier.height(5.dp))
        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            val arrowPosition = if (question.downVoteCount > 1) painterResource(id = R.drawable.ic_arrow_down) else painterResource(
                id = R.drawable.ic_arrow_up
            )
            val voteCount = if (question.downVoteCount > 1) "-${question.downVoteCount}" else "${question.upVoteCount}"

            QuestionCount(
                icon = arrowPosition,
                count = voteCount.toInt()
            )
            Spacer(modifier = modifier.width(10.dp))
            QuestionCount(
                icon = painterResource(id = R.drawable.ic_answer),
                count = question.answerCount
            )
            Spacer(modifier = modifier.width(10.dp))
            QuestionCount(
                icon = painterResource(id = R.drawable.ic_views),
                count = question.viewCount
            )
        }
    }
}
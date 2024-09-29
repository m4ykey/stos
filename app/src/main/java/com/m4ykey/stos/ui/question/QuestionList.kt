package com.m4ykey.stos.ui.question

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.m4ykey.network.data.model.Question
import com.m4ykey.stos.R
import com.m4ykey.stos.ui.components.list.LazyVerticalColumn
import com.m4ykey.stos.ui.components.ui.OwnerProfile
import com.m4ykey.stos.ui.components.ui.QuestionCount
import dev.jeziellago.compose.markdowntext.MarkdownText
import org.koin.androidx.compose.koinViewModel

@Composable
fun QuestionList(
    modifier : Modifier = Modifier,
    viewModel: QuestionViewModel = koinViewModel(),
    sort: String,
    onQuestionClick: (Int) -> Unit,
    onOwnerClick: (Int) -> Unit
) {
    val uiState by viewModel.questions.collectAsState()
    val questionList: LazyPagingItems<Question> = uiState.questionList.collectAsLazyPagingItems()

    LaunchedEffect(sort) {
        if (viewModel.shouldLoadData(sort)) {
            viewModel.getQuestions(sort = sort)
        }
    }

    LazyVerticalColumn(
        items = questionList,
        key = { question -> question.questionId },
        onItemContent = { question ->
            QuestionItem(
                question = question,
                onQuestionClick = onQuestionClick,
                onOwnerClick = onOwnerClick
            )
            HorizontalDivider()
        },
        onLoadingContent = { CircularProgressIndicator() },
        onErrorContent = { Text(text = "Error loading items") },
        modifier = modifier.fillMaxSize()
    )
}

@Composable
fun QuestionItem(
    modifier: Modifier = Modifier,
    question: Question,
    onQuestionClick: (Int) -> Unit,
    onOwnerClick : (Int) -> Unit
) {
    Column(
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable { onQuestionClick(question.questionId) }
    ) {
        OwnerProfile(
            owner = question.owner,
            size = 26.dp,
            isBadgeCounts = false,
            onOwnerClick = onOwnerClick
        )
        Spacer(modifier = modifier.height(5.dp))
        MarkdownText(markdown = question.title)
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
package com.m4ykey.stos.ui.screen.question

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.m4ykey.network.data.model.Question
import com.m4ykey.stos.R
import com.m4ykey.stos.ui.components.list.LazyVerticalColumn
import com.m4ykey.stos.ui.components.ui.owner.OwnerProfile
import com.m4ykey.stos.ui.components.ui.QuestionCount
import com.m4ykey.stos.ui.components.ui.format.formatCreationDate
import dev.jeziellago.compose.markdowntext.MarkdownText
import org.koin.androidx.compose.koinViewModel

@Composable
fun QuestionList(
    modifier: Modifier = Modifier,
    viewModel: QuestionViewModel = koinViewModel(),
    onQuestionClick: (Int) -> Unit,
    onOwnerClick: (Int) -> Unit
) {
    val uiState by viewModel.questions.collectAsState()
    val questionList: LazyPagingItems<Question> = uiState.questionList.collectAsLazyPagingItems()

    LazyVerticalColumn(
        modifier = modifier.fillMaxSize(),
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
        onErrorContent = { Text(text = "Error loading items") }
    )
}

@Composable
fun QuestionItem(
    modifier: Modifier = Modifier,
    question: Question,
    onQuestionClick: (Int) -> Unit,
    onOwnerClick: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable { onQuestionClick(question.questionId) }
    ) {
        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            OwnerProfile(
                owner = question.owner,
                size = 26.dp,
                isBadgeCounts = false,
                onOwnerClick = onOwnerClick,
                modifier = modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = modifier.weight(1f))
            Text(
                text = formatCreationDate(creationDate = question.creationDate),
                fontSize = 13.sp,
                modifier = modifier.align(Alignment.CenterVertically)
            )
        }
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
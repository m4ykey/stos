package com.m4ykey.stos.ui.owner

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.m4ykey.network.data.model.Answer
import com.m4ykey.network.data.model.Owner
import com.m4ykey.network.data.model.Question
import com.m4ykey.stos.R
import com.m4ykey.stos.ui.components.list.LoadStateView
import com.m4ykey.stos.ui.components.ui.ChipItem
import com.m4ykey.stos.ui.components.ui.ErrorScreen
import com.m4ykey.stos.ui.components.ui.OwnerProfile
import com.m4ykey.stos.ui.components.ui.OwnerProfileCard
import com.m4ykey.stos.ui.components.ui.Progressbar
import com.m4ykey.stos.ui.components.ui.QuestionCount
import com.m4ykey.stos.ui.components.ui.ReputationAndBadgeRow
import com.m4ykey.stos.ui.question.QuestionItem
import dev.jeziellago.compose.markdowntext.MarkdownText
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerScreen(
    modifier: Modifier = Modifier,
    onNavigateBack : () -> Unit,
    ownerId : Int,
    viewModel: OwnerViewModel = koinViewModel(),
    onQuestionClick: (Int) -> Unit
) {

    LaunchedEffect(ownerId) {
        viewModel.getOwnerQuestion(ownerId)
    }

    val uiState by viewModel.owner.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {  },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        when {
            uiState.isLoading -> { Progressbar() }
            uiState.isError != null -> { ErrorScreen(text = "Error: ${uiState.isError}") }
            uiState.owner != null -> {
                OwnerContent(
                    modifier = modifier.padding(innerPadding),
                    owner = uiState.owner!!,
                    onQuestionClick = onQuestionClick
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OwnerContent(
    modifier: Modifier = Modifier,
    owner : Owner,
    viewModel: OwnerViewModel = koinViewModel(),
    onQuestionClick : (Int) -> Unit
) {

    var isQuestionSelected by remember { mutableStateOf(true) }
    var isAnswerSelected by remember { mutableStateOf(false) }

    val uiQuestionState by viewModel.ownerQuestions.collectAsState()
    val uiAnswerState by viewModel.ownerAnswers.collectAsState()

    val questionList : LazyPagingItems<Question> = uiQuestionState.questionList.collectAsLazyPagingItems()
    val answerList : LazyPagingItems<Answer> = uiAnswerState.questionAnswerList.collectAsLazyPagingItems()

    CompositionLocalProvider(
        value = LocalOverscrollConfiguration provides null
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item { OwnerProfileCard(owner = owner, size = 120.dp) }
            item {
                MarkdownText(
                    markdown = owner.displayName,
                    style = TextStyle(fontSize = 20.sp)
                )
            }
            owner.location?.let { location ->
                item { LocationRow(location = location) }
            }
            item { ReputationAndBadgeRow(owner = owner) }
            item {
                Row(
                    modifier = modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ChipItem(
                        modifier = modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        title = stringResource(id = R.string.questions),
                        selected = isQuestionSelected,
                        onSelected = {
                            isQuestionSelected = true
                            isAnswerSelected = false
                        }
                    )
                    Spacer(modifier = modifier.width(8.dp))
                    ChipItem(
                        modifier = modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        title = stringResource(id = R.string.answers),
                        selected = isAnswerSelected,
                        onSelected = {
                            isAnswerSelected = true
                            isQuestionSelected = false
                        }
                    )
                }
            }
            if (isQuestionSelected) {
                items(
                    count = questionList.itemCount,
                    contentType = questionList.itemContentType { "Questions" },
                    key = questionList.itemKey { question -> question.questionId }
                ) { index ->
                    val question = questionList[index]
                    if (question != null) {
                        QuestionItem(
                            question = question,
                            onQuestionClick = onQuestionClick,
                            onOwnerClick = {  }
                        )
                        HorizontalDivider()
                    }
                }
            } else if (isAnswerSelected) {
                items(
                    count = answerList.itemCount,
                    contentType = answerList.itemContentType { "Answers" },
                    key = answerList.itemKey { answer -> answer.answerId }
                ) { index ->
                    val answer = answerList[index]
                    if (answer != null) {
                        AnswerListItem(
                            answer = answer,
                            onAnswerClick = onQuestionClick
                        )
                        HorizontalDivider()
                    }
                }
            }
            item { LoadStateView(loadState = questionList.loadState.append) }
            item { LoadStateView(loadState = questionList.loadState.refresh) }
        }
    }
}

@Composable
fun LocationRow(location: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Outlined.LocationOn,
            contentDescription = stringResource(id = R.string.location)
        )
        Text(text = location)
    }
}

@Composable
fun AnswerListItem(
    modifier: Modifier = Modifier,
    answer: Answer,
    onAnswerClick : (Int) -> Unit
) {
    Column(
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable { onAnswerClick(answer.questionId) }
    ) {
        OwnerProfile(
            owner = answer.owner,
            size = 26.dp,
            isBadgeCounts = false,
            onOwnerClick = {  }
        )
        Spacer(modifier = modifier.height(5.dp))
        MarkdownText(markdown = answer.title)
        Spacer(modifier = modifier.height(5.dp))
        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            val arrowPosition = if (answer.downVoteCount > 1) painterResource(id = R.drawable.ic_arrow_down) else painterResource(
                id = R.drawable.ic_arrow_up
            )
            val voteCount = if (answer.downVoteCount > 1) "-${answer.downVoteCount}" else "${answer.upVoteCount}"

            QuestionCount(
                icon = arrowPosition,
                count = voteCount.toInt()
            )
            Spacer(modifier = modifier.width(10.dp))
            QuestionCount(
                icon = painterResource(id = R.drawable.ic_answer),
                count = answer.commentCount
            )
        }
    }
}
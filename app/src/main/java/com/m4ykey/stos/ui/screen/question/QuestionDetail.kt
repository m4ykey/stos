package com.m4ykey.stos.ui.screen.question

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.m4ykey.network.data.model.Answer
import com.m4ykey.network.data.model.ClosedDetails
import com.m4ykey.network.data.model.QuestionDetail
import com.m4ykey.stos.R
import com.m4ykey.stos.ui.components.list.LoadStateView
import com.m4ykey.stos.ui.components.ui.ErrorScreen
import com.m4ykey.stos.ui.components.ui.OwnerProfile
import com.m4ykey.stos.ui.components.ui.Progressbar
import com.m4ykey.stos.ui.components.ui.formatCreationDate
import com.m4ykey.stos.util.openUrlBrowser
import com.m4ykey.stos.util.processHtmlEntities
import com.m4ykey.stos.util.shareUrl
import dev.jeziellago.compose.markdowntext.MarkdownText
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionDetail(
    modifier: Modifier = Modifier,
    questionId : Int,
    onNavigateBack : () -> Unit,
    onTagClick: (String) -> Unit,
    viewModel: QuestionViewModel = koinViewModel(),
    onOwnerClick: (Int) -> Unit
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val uiDetailState by viewModel.questionDetail.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(questionId) {
        viewModel.getQuestionDetailAnswer(questionId)
    }

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = {  },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { shareUrl(uiDetailState.questionDetail?.link.orEmpty(), context) }) {
                        Icon(
                            imageVector = Icons.Outlined.Share,
                            contentDescription = stringResource(id = R.string.share_url)
                        )
                    }
                    IconButton(onClick = { openUrlBrowser(context, uiDetailState.questionDetail?.link.orEmpty()) }) {
                        Icon(
                            imageVector = Icons.Outlined.Public,
                            contentDescription = stringResource(id = R.string.open_url_in_browser)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        when {
            uiDetailState.isLoading -> { Progressbar() }
            uiDetailState.isError != null -> { ErrorScreen(text = "Error: ${uiDetailState.isError}") }
            uiDetailState.questionDetail != null -> {
                QuestionDetailContent(
                    modifier = modifier.padding(innerPadding),
                    questionDetail = uiDetailState.questionDetail!!,
                    onTagClick = onTagClick,
                    onOwnerClick = onOwnerClick
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuestionDetailContent(
    modifier: Modifier = Modifier,
    questionDetail: QuestionDetail,
    onTagClick: (String) -> Unit,
    viewModel: QuestionViewModel = koinViewModel(),
    onOwnerClick : (Int) -> Unit
) {
    val uiAnswerState by viewModel.questionAnswer.collectAsState()
    val answerList : LazyPagingItems<Answer> = uiAnswerState.questionAnswerList.collectAsLazyPagingItems()

    CompositionLocalProvider(
        value = LocalOverscrollConfiguration provides null
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val closedDetails = questionDetail.closedDetails
            if (closedDetails.reason.isNotEmpty() && closedDetails.description.isNotEmpty()) {
                item {
                    ClosedCard(closedDetails = questionDetail.closedDetails)
                }
            }
            item {
                MarkdownText(
                    markdown = processHtmlEntities(questionDetail.title),
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
            item {
                MarkdownText(
                    markdown = processHtmlEntities(questionDetail.bodyMarkdown),
                    linkColor = Color(0xFF2367DD)
                )
            }
            item {
                ChipGroup(
                    tags = questionDetail.tags,
                    onTagClick = { tag -> onTagClick(tag) }
                )
            }
            item {
                Column {
                    Text(
                        text = stringResource(R.string.asked) + " "
                                + formatCreationDate(creationDate = questionDetail.creationDate),
                        fontSize = 13.sp
                    )
                    OwnerProfile(
                        owner = questionDetail.owner,
                        size = 30.dp,
                        isBadgeCounts = true,
                        onOwnerClick = onOwnerClick
                    )
                }
            }
            item { HorizontalDivider() }
            if (answerList.itemCount == 0) {
                item {
                    Text(text = stringResource(id = R.string.no_answers))
                }
            } else {
                item {
                    Text(
                        text = stringResource(R.string.answers) + ": " + answerList.itemCount.toString(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                items(
                    count = answerList.itemCount,
                    key = answerList.itemKey { answer -> answer.answerId },
                    contentType = answerList.itemContentType { "Answers" },
                ) { index ->
                    val answer = answerList[index]
                    if (answer != null) {
                        AnswerItem(
                            answer = answer,
                            onOwnerClick = onOwnerClick
                        )
                        if (index < answerList.itemCount - 1) {
                            HorizontalDivider()
                        }
                    }
                }

                item { LoadStateView(loadState = answerList.loadState.append) }
                item { LoadStateView(loadState = answerList.loadState.refresh) }
            }
        }
    }
}

@Composable
fun AnswerItem(
    modifier: Modifier = Modifier,
    answer: Answer,
    onOwnerClick: (Int) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        MarkdownText(markdown = processHtmlEntities(answer.bodyMarkdown))
        OwnerProfile(
            owner = answer.owner,
            size = 30.dp,
            isBadgeCounts = true,
            onOwnerClick = onOwnerClick
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChipGroup(
    modifier : Modifier = Modifier,
    tags : List<String>,
    onTagClick : (String) -> Unit
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        tags.forEach { tag ->
            FilterChip(
                label = { Text(text = tag) },
                onClick = { onTagClick(tag) },
                selected = false
            )
        }
    }
}

@Composable
fun ClosedCard(
    modifier: Modifier = Modifier,
    closedDetails: ClosedDetails
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.fillMaxWidth(),
        content = {
            Column(
                modifier = modifier
                    .padding(10.dp)
                    .fillMaxSize()
            ) {
                MarkdownText(
                    markdown = processHtmlEntities(closedDetails.reason),
                    style = TextStyle(
                        color = Color.White
                    )
                )
                Spacer(modifier = modifier.height(10.dp))
                MarkdownText(
                    markdown = processHtmlEntities(closedDetails.description),
                    style = TextStyle(
                        color = Color.White
                    )
                )
            }
        },
        colors = CardDefaults.cardColors(containerColor = Color.Red)
    )
}
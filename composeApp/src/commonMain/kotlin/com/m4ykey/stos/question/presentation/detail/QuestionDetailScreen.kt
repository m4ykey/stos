package com.m4ykey.stos.question.presentation.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.m4ykey.stos.core.network.openBrowser
import com.m4ykey.stos.question.data.mappers.toQuestion
import com.m4ykey.stos.question.domain.model.QuestionAnswer
import com.m4ykey.stos.question.domain.model.QuestionDetail
import com.m4ykey.stos.question.domain.model.QuestionOwner
import com.m4ykey.stos.question.presentation.components.BadgeRow
import com.m4ykey.stos.question.presentation.components.ErrorComponent
import com.m4ykey.stos.question.presentation.components.MarkdownText
import com.m4ykey.stos.question.presentation.components.chip.ChipItem
import com.m4ykey.stos.question.presentation.components.formatCreationDate
import com.m4ykey.stos.question.presentation.components.formatReputation
import com.m4ykey.stos.question.presentation.components.list_items.AnswerItem
import com.m4ykey.stos.question.presentation.components.list_items.QuestionStatsRow
import com.m4ykey.stos.user.presentation.components.OwnerCard
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import stos.composeapp.generated.resources.Res
import stos.composeapp.generated.resources.answers
import stos.composeapp.generated.resources.asked
import stos.composeapp.generated.resources.back
import stos.composeapp.generated.resources.link

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionDetailScreen(
    modifier : Modifier = Modifier,
    id : Int,
    onNavBack : () -> Unit,
    viewModel: QuestionDetailViewModel = koinViewModel(),
    onTagClick : (String) -> Unit,
    onOwnerClick : (Int) -> Unit
) {

    val detailState by viewModel.qDetailState.collectAsState()
    val answerState by viewModel.qAnswerState.collectAsState()

    val isLoading = detailState.isLoading || answerState.isLoading
    val errorMessage = detailState.errorMessage ?: answerState.errorMessage

    val questionDetail = detailState.question
    val answerList = answerState.answers

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is DetailUiEvent.OpenLink -> openBrowser(event.url)
                is DetailUiEvent.NavigateToTag -> onTagClick(event.tag)
                is DetailUiEvent.NavigateToUser -> onOwnerClick(event.userId)
            }
        }
    }

    LaunchedEffect(id) {
        viewModel.loadQuestionDetails(id)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                actions = {
                    val link = questionDetail?.link
                    if (!link.isNullOrEmpty()) {
                        IconButton(onClick = { openBrowser(link) }) {
                            Icon(
                                imageVector = Icons.Outlined.Public,
                                contentDescription = stringResource(resource = Res.string.link)
                            )
                        }
                    }
                },
                title = {},
                navigationIcon = {
                    IconButton(onClick = onNavBack) {
                        Icon(
                            contentDescription = stringResource(resource = Res.string.back),
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                errorMessage != null -> {
                    ErrorComponent(errorMessage)
                }
                questionDetail != null -> {
                    QuestionDetailContent(
                        item = questionDetail,
                        paddingValues = padding,
                        listState = listState,
                        onAction = viewModel::onAction,
                        answers = answerList
                    )
                }
                else -> {
                    Text(
                        fontSize = 16.sp,
                        modifier = Modifier.align(Alignment.Center),
                        text = "No data"
                    )
                }
            }
        }
    }
}

@Composable
fun QuestionDetailContent(
    modifier : Modifier = Modifier,
    item : QuestionDetail,
    paddingValues : PaddingValues,
    onAction : (QuestionDetailAction) -> Unit,
    listState : LazyListState,
    answers : List<QuestionAnswer>
) {
    LazyColumn (
        state = listState,
        modifier = modifier
            .padding(paddingValues)
            .padding(horizontal = 10.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            MarkdownText(
                content = item.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
            MarkdownText(
                content = item.bodyMarkdown
            )
        }
        item {
            TagListWrap(
                tags = item.tags,
                onTagClick = { tag ->
                    onAction(QuestionDetailAction.OnTagClick(tag))
                }
            )
        }
        item {
            QuestionStatsRow(
                item = item.toQuestion(),
                iconSize = 16.dp,
                textSize = 15.sp
            )
        }
        item {
            Text(
                text = "${stringResource(resource = Res.string.asked)} ${formatCreationDate(item.creationDate.toLong())}",
                fontSize = 14.sp
            )
        }
        item {
            DisplayOwner(
                item = item.owner,
                onOwnerClick = {
                    item.owner.userId.let { id ->
                        onAction(QuestionDetailAction.OnOwnerClick(id))
                    }
                }
            )
        }
        item {
            Text(
                text = "${stringResource(resource = Res.string.answers)}: ${item.answerCount}"
            )
        }
        items(
            items = answers,
            key = { it.answerId },
            contentType = { "answer_item" }
        ) { answer ->
            AnswerItem(
                onOwnerClick = {
                    answer.owner.userId.let { id ->
                        onAction(QuestionDetailAction.OnOwnerClick(id))
                    }
                },
                owner = answer.owner,
                answer = answer
            )
            Spacer(modifier = Modifier.height(5.dp))
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 5.dp)
            )
        }
    }
}

@Composable
fun DisplayOwner(
    modifier : Modifier = Modifier,
    onOwnerClick : (Int) -> Unit,
    item : QuestionOwner
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        OwnerCard(
            owner = item,
            modifier = modifier
                .align(Alignment.CenterVertically)
                .clickable { onOwnerClick(item.userId) }
        )
        Spacer(modifier = modifier.width(5.dp))
        Column(
            modifier = modifier.weight(1f)
        ) {
            MarkdownText(
                content = item.displayName
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    fontSize = 13.sp,
                    text = formatReputation(item.reputation)
                )
                BadgeRow(item.badgeCounts)
            }
        }
    }
}

@Composable
fun TagListWrap(
    tags : List<String>,
    onTagClick: (String) -> Unit
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tags.forEach { label ->
            ChipItem(
                title = label,
                modifier = Modifier,
                selected = false,
                onSelect = { onTagClick(label) }
            )
        }
    }
}
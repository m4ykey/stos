package com.m4ykey.stos.question.presentation.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.m4ykey.stos.core.network.openBrowser
import com.m4ykey.stos.owner.presentation.components.OwnerCard
import com.m4ykey.stos.question.data.mappers.toQuestion
import com.m4ykey.stos.question.domain.model.Owner
import com.m4ykey.stos.question.domain.model.QuestionDetail
import com.m4ykey.stos.question.presentation.components.BadgeRow
import com.m4ykey.stos.question.presentation.components.MarkdownText
import com.m4ykey.stos.question.presentation.components.QuestionStatsRow
import com.m4ykey.stos.question.presentation.components.chip.ChipItem
import com.m4ykey.stos.question.presentation.components.formatCreationDate
import com.m4ykey.stos.question.presentation.components.formatReputation
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import stos.composeapp.generated.resources.Res
import stos.composeapp.generated.resources.asked

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

    LaunchedEffect(id) {
        viewModel.loadQuestionById(id)
    }

    val state by viewModel.qDetailState.collectAsState()
    val isLoading = state.isLoading
    val errorMessage = state.errorMessage
    val questionDetail = state.question

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    LaunchedEffect(viewModel) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is DetailUiEvent.OpenLink -> openBrowser(event.url)
                is DetailUiEvent.NavigateToTag -> onTagClick(event.tag)
                is DetailUiEvent.NavigateToUser -> onOwnerClick(event.userId)
            }
        }
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
                                contentDescription = "Link"
                            )
                        }
                    }
                },
                title = {},
                navigationIcon = {
                    IconButton(onClick = onNavBack) {
                        Icon(
                            contentDescription = null,
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
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        fontSize = 16.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                questionDetail != null -> {
                    QuestionDetailContent(
                        item = questionDetail,
                        paddingValues = padding,
                        onAction = viewModel::onAction
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
    onAction : (QuestionDetailAction) -> Unit
) {
    LazyColumn (
        modifier = modifier
            .padding(paddingValues)
            .padding(horizontal = 10.dp)
            .fillMaxSize()
    ) {
        item {
            MarkdownText(
                content = item.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(10.dp))
            MarkdownText(
                content = item.bodyMarkdown
            )
        }
        item {
            TagListRow(
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
    }
}

@Composable
fun DisplayOwner(
    modifier : Modifier = Modifier,
    onOwnerClick : (Int) -> Unit,
    item : Owner
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            OwnerCard(
                modifier = Modifier
                    .clickable { onOwnerClick(item.userId) }
                    .align(Alignment.CenterVertically),
                owner = item
            )
            Spacer(modifier = Modifier.width(5.dp))
            Column(modifier = modifier.fillMaxWidth()) {
                MarkdownText(
                    content = item.displayName
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        fontSize = 13.sp,
                        text = formatReputation(item.reputation)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    BadgeRow(item.badgeCounts)
                }
            }
        }
    }
}

@Composable
fun TagListRow(
    modifier : Modifier = Modifier,
    tags : List<String>,
    onTagClick: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(
            items = tags,
            key = { it }
        ) { label ->
            ChipItem(
                title = label,
                modifier = modifier.padding(horizontal = 5.dp),
                selected = false,
                onSelect = { onTagClick(label) }
            )
        }
    }
}
package com.m4ykey.stos.ui.question

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.m4ykey.network.data.model.QuestionDetail
import com.m4ykey.stos.ui.components.OwnerProfile
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
    viewModel: QuestionViewModel = koinViewModel()
) {

    val uiState by viewModel.questionDetail.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(questionId) {
        viewModel.getQuestionDetail(questionId)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {  },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { shareUrl(uiState.questionDetail?.link.orEmpty(), context) }) {
                        Icon(
                            imageVector = Icons.Outlined.Share,
                            contentDescription = null
                        )
                    }
                    IconButton(onClick = { openUrlBrowser(context, uiState.questionDetail?.link.orEmpty()) }) {
                        Icon(
                            imageVector = Icons.Outlined.Public,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        when {
            uiState.isLoading -> {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            uiState.isError != null -> {}
            uiState.questionDetail != null -> {
                QuestionDetailContent(
                    modifier = modifier.padding(innerPadding),
                    questionDetail = uiState.questionDetail!!,
                    onTagClick = onTagClick
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
    onTagClick: (String) -> Unit
) {
    CompositionLocalProvider(
        value = LocalOverscrollConfiguration provides null
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                .verticalScroll(rememberScrollState())
        ) {
            MarkdownText(
                markdown = questionDetail.title,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                ),
                modifier = modifier.padding(bottom = 10.dp)
            )
            MarkdownText(
                markdown = processHtmlEntities(questionDetail.bodyMarkdown),
                linkColor = Color(0xFF2367DD)
            )
            ChipGroup(
                tags = questionDetail.tags,
                onTagClick = { tag -> onTagClick(tag) }
            )
            OwnerProfile(
                owner = questionDetail.owner,
                size = 30.dp,
                isBadgeCounts = true
            )
        }
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
        modifier = modifier
            .fillMaxWidth(),
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
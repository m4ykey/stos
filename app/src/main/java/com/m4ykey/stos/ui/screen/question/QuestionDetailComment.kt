package com.m4ykey.stos.ui.screen.question

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.m4ykey.network.data.model.Comment
import com.m4ykey.stos.R
import com.m4ykey.stos.ui.components.list.LazyVerticalColumn
import com.m4ykey.stos.ui.components.ui.OwnerProfile
import com.m4ykey.stos.ui.components.ui.formatCreationDate
import dev.jeziellago.compose.markdowntext.MarkdownText
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionDetailComment(
    modifier: Modifier = Modifier,
    questionId : Int,
    onNavigateBack : () -> Unit,
    onOwnerClick : (Int) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.comments)) },
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                },
            )
        }
    ) { paddingValues ->
        CommentList(
            modifier = modifier.padding(paddingValues),
            onOwnerClick = onOwnerClick,
            questionId = questionId
        )
    }
}

@Composable
fun CommentList(
    modifier: Modifier = Modifier,
    viewModel: QuestionViewModel = koinViewModel(),
    onOwnerClick : (Int) -> Unit,
    questionId: Int
) {
    LaunchedEffect(questionId) {
        viewModel.getQuestionComments(questionId)
    }

    val uiState by viewModel.questionComment.collectAsState()
    val commentList : LazyPagingItems<Comment> = uiState.commentList.collectAsLazyPagingItems()

    LazyVerticalColumn(
        modifier = modifier.fillMaxSize(),
        items = commentList,
        key = { comment -> comment.commentId },
        onItemContent = { comment ->
            CommentItem(
                comment = comment,
                onOwnerClick = onOwnerClick
            )
            HorizontalDivider()
        },
        onLoadingContent = { CircularProgressIndicator() },
        onErrorContent = { Text(text = "Error loading items") }
    )
}

@Composable
fun CommentItem(
    modifier: Modifier = Modifier,
    comment: Comment,
    onOwnerClick : (Int) -> Unit
) {
    Column(
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.commented) + " " + formatCreationDate(creationDate = comment.creationDate),
            fontSize = 13.sp
        )
        MarkdownText(markdown = comment.bodyMarkdown)
        OwnerProfile(
            owner = comment.owner,
            size = 26.dp,
            isBadgeCounts = false,
            onOwnerClick = onOwnerClick
        )
    }
}
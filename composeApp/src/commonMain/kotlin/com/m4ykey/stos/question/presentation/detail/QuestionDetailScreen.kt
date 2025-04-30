package com.m4ykey.stos.question.presentation.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.m4ykey.stos.core.data.htmlDecode
import com.m4ykey.stos.question.domain.model.QuestionDetail
import com.m4ykey.stos.question.presentation.QuestionViewModel
import com.m4ykey.stos.question.presentation.components.chip.ChipItem
import com.mikepenz.markdown.m3.Markdown
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionDetailScreen(
    modifier : Modifier = Modifier,
    id : Int,
    onNavBack : () -> Unit,
    viewModel: QuestionViewModel = koinViewModel(),
    onTagClick : (String) -> Unit
) {

    LaunchedEffect(key1 = id) {
        viewModel.loadQuestionById(id)
    }

    val state by viewModel.qDetailState.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(onClick = {}) {

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
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        if (state.errorMessage != null) {

        }
        if (state.question != null) {
            QuestionDetailContent(
                item = state.question!!,
                onTagClick = onTagClick,
                paddingValues = padding
            )
        }
    }
}

@Composable
fun QuestionDetailContent(
    modifier : Modifier = Modifier,
    item : QuestionDetail,
    paddingValues : PaddingValues,
    onTagClick : (String) -> Unit
) {
    LazyColumn (
        modifier = modifier
            .padding(paddingValues)
            .padding(horizontal = 10.dp)
            .fillMaxSize()
    ) {
        item {
            Markdown(
                content = item.title.htmlDecode()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Markdown(
                content = item.bodyMarkdown.htmlDecode()
            )
        }
        item {
            TagListRow(
                tags = item.tags,
                onTagClick = onTagClick
            )
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
        items(tags) { label ->
            ChipItem(
                title = label,
                modifier = modifier.padding(horizontal = 5.dp),
                selected = false,
                onSelect = { onTagClick(label) }
            )
        }
    }
}
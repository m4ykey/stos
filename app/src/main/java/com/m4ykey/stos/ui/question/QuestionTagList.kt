package com.m4ykey.stos.ui.question

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.m4ykey.network.data.model.Question
import com.m4ykey.stos.ui.components.ChipList
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionTagList(
    modifier: Modifier = Modifier,
    tag: String,
    onNavigateBack: () -> Unit,
    onQuestionClick: (Int) -> Unit
) {

    var sortType by remember { mutableStateOf("hot") }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = tag) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding)
        ) {
            Spacer(modifier = modifier.height(5.dp))
            ChipList(
                onSortSelected = { sortType = it },
                selectedSort = sortType
            )
            Spacer(modifier = modifier.height(5.dp))
            QuestionTagList(
                tag = tag,
                sort = sortType,
                onQuestionClick = onQuestionClick
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuestionTagList(
    modifier : Modifier = Modifier,
    viewModel: QuestionViewModel = koinViewModel(),
    onQuestionClick: (Int) -> Unit,
    tag: String,
    sort : String
) {

    val uiState by viewModel.questions.collectAsState()
    val questionList: LazyPagingItems<Question> = uiState.questionList.collectAsLazyPagingItems()

    LaunchedEffect(tag) {
        if (viewModel.shouldLoadTagData(tag, sort)) {
            viewModel.getQuestionsTag(tag = tag, sort = sort)
        }
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
                    QuestionItem(
                        question = question,
                        onQuestionClick = onQuestionClick
                    )
                    HorizontalDivider()
                }
            }

            when (questionList.loadState.append) {
                is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
                is LoadState.Error -> {
                    item {
                        Box(
                            modifier = modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Error loading items")
                        }
                    }
                }
                else -> Unit
            }

            when (questionList.loadState.refresh) {
                is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
                is LoadState.Error -> {
                    item {
                        Box(
                            modifier = modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Error loading items")
                        }
                    }
                }
                else -> Unit
            }
        }
    }
}
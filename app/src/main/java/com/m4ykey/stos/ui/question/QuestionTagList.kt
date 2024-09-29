package com.m4ykey.stos.ui.question

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.m4ykey.network.data.model.Question
import com.m4ykey.stos.R
import com.m4ykey.stos.ui.components.list.LazyVerticalColumn
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionTagList(
    modifier: Modifier = Modifier,
    tag: String,
    onNavigateBack: () -> Unit,
    onQuestionClick: (Int) -> Unit,
    onOwnerClick: (Int) -> Unit
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
                            contentDescription = stringResource(id = R.string.back)
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
                onQuestionClick = onQuestionClick,
                onOwnerClick = onOwnerClick
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
    sort : String,
    onOwnerClick : (Int) -> Unit
) {

    val uiState by viewModel.questions.collectAsState()
    val questionList: LazyPagingItems<Question> = uiState.questionList.collectAsLazyPagingItems()

    LaunchedEffect(tag) {
        if (viewModel.shouldLoadData(tag, sort)) {
            viewModel.getQuestionsTag(tag = tag, sort = sort)
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
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
    )
}
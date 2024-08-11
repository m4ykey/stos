package com.m4ykey.stos.ui.question

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.m4ykey.network.data.model.QuestionDetail
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionDetail(
    modifier: Modifier = Modifier,
    questionId : Int,
    onNavigateBack : () -> Unit,
    viewModel: QuestionViewModel = koinViewModel()
) {

    val uiState by viewModel.questionDetail.collectAsState()

    LaunchedEffect(Unit) {
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
                }
            )
        }
    ) { innerPadding ->
        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
            }
            uiState.isError != null -> {}
            uiState.questionDetail != null -> {
                QuestionDetailContent(
                    modifier = modifier.padding(innerPadding),
                    questionDetail = uiState.questionDetail!!
                )
            }
        }
    }
}

@Composable
fun QuestionDetailContent(
    modifier: Modifier = Modifier,
    questionDetail: QuestionDetail
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = questionDetail.bodyMarkdown)
    }
}
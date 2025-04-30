package com.m4ykey.stos.question.presentation.detail

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.m4ykey.stos.question.presentation.QuestionViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionDetailScreen(
    id : Int,
    onNavBack : () -> Unit,
    viewModel: QuestionViewModel = koinViewModel()
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
                title = {
                    Text(
                        text = state.question?.title ?: ""
                    )
                },
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

    }
}
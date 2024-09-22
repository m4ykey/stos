package com.m4ykey.stos.ui.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.m4ykey.network.data.model.Question
import com.m4ykey.stos.R
import com.m4ykey.stos.ui.components.list.LazyVerticalColumn
import com.m4ykey.stos.ui.question.QuestionItem
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onNavigateBack : () -> Unit,
    viewModel: SearchViewModel = koinViewModel(),
    onQuestionClick : (Int) -> Unit,
    onOwnerClick : (Int) -> Unit
) {

    val uiState by viewModel.search.collectAsState()
    val searchList : LazyPagingItems<Question> = uiState.searchList.collectAsLazyPagingItems()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.search)) },
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
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            SearchComponent()
            LazyVerticalColumn(
                modifier = modifier.fillMaxSize(),
                items = searchList,
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
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchComponent(modifier: Modifier = Modifier) {
    var text by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize()) {
        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    query = text,
                    onQueryChange = { text = it },
                    onSearch = { expanded = false },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    trailingIcon = {
                        if (text.isNotEmpty()) Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    },
                    placeholder = { Text(text = stringResource(id = R.string.search_questions)) },
                    leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = stringResource(id = R.string.search)) }
                )
            },
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {

        }
    }
}
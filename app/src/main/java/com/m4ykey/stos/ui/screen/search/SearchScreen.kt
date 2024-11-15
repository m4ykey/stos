package com.m4ykey.stos.ui.screen.search

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.m4ykey.network.data.model.Question
import com.m4ykey.stos.R
import com.m4ykey.stos.ui.components.list.LazyVerticalColumn
import com.m4ykey.stos.ui.screen.question.ChipGroup
import com.m4ykey.stos.ui.screen.question.QuestionItem
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    viewModel: SearchViewModel = koinViewModel(),
    onQuestionClick: (Int) -> Unit,
    onOwnerClick: (Int) -> Unit
) {

    val uiState by viewModel.search.collectAsState()
    val searchList: LazyPagingItems<Question> = uiState.searchList.collectAsLazyPagingItems()

    var isSearching by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    val tags by remember { mutableStateOf(listOf("java", "kotlin", "android")) }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
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
            SearchComponent(onSearch = { isSearching = true })
            Spacer(modifier = modifier.height(10.dp))
            if (expanded) {
                ChipGroup(
                    tags = tags,
                    onTagClick = { tag -> viewModel.searchQuestions(null, tag) },
                    modifier = modifier.padding(start = 10.dp, end = 10.dp)
                )
            }
            TagExpandedButton(
                expanded = expanded,
                onClick = { expanded = !expanded },
                modifier = modifier.align(Alignment.CenterHorizontally)
            )
            LazyVerticalColumn(
                modifier = modifier
                    .padding(10.dp)
                    .fillMaxSize(),
                items = searchList,
                onItemContent = { question ->
                    QuestionItem(
                        question = question,
                        onQuestionClick = onQuestionClick,
                        onOwnerClick = onOwnerClick
                    )
                    HorizontalDivider()
                },
                onLoadingContent = {
                    if (isSearching) CircularProgressIndicator()
                },
                onErrorContent = { Text(text = "Error loading items") }
            )
        }
    }
}

@Composable
fun TagExpandedButton(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onClick: () -> Unit
) {
    val rotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = tween(durationMillis = 250),
        label = "button animation"
    )

    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = null,
            modifier = modifier.graphicsLayer(rotationZ = rotation)
        )
    }
}

@Composable
fun SearchComponent(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = koinViewModel(),
    onSearch: () -> Unit
) {
    var text by remember { mutableStateOf("") }

    Box(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = text,
            onValueChange = { newText -> text = newText },
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            label = { Text(text = stringResource(id = R.string.search_questions)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search)
                )
            },
            trailingIcon = {
                if (text.isNotEmpty()) {
                    IconButton(onClick = { text = "" }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }
                }
            },
            keyboardActions = KeyboardActions(
                onSearch = {
                    if (text.isNotBlank()) {
                        viewModel.searchQuestions(text, null)
                        onSearch()
                    }
                }
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            )
        )
    }
}
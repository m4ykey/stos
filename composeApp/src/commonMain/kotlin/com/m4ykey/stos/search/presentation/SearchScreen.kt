package com.m4ykey.stos.search.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.m4ykey.stos.question.presentation.detail.TagListWrap
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import stos.composeapp.generated.resources.Res
import stos.composeapp.generated.resources.back
import stos.composeapp.generated.resources.popular_tags
import stos.composeapp.generated.resources.search
import stos.composeapp.generated.resources.search_placeholder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onNavBack : () -> Unit,
    viewModel: SearchViewModel = koinViewModel(),
    onSearchScreen : (String) -> Unit
) {

    var inTitle by remember { mutableStateOf("") }
    var tag by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val listState = remember { LazyListState() }

    LaunchedEffect(Unit) {
        viewModel.listUiEvent.collectLatest { event ->
            when (event) {
                else -> null
            }
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = { Text(text = stringResource(resource = Res.string.search)) },
                navigationIcon = {
                    IconButton(onClick = onNavBack) {
                        Icon(
                            contentDescription = stringResource(resource = Res.string.back),
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack
                        )
                    }
                }
            )
        }
    ) { padding ->
        SearchContent(
            padding = padding,
            listState = listState,
            inTitle = inTitle,
            onSearch = { searchQuery = inTitle },
            onInTitleChange = { inTitle = it },
            onTagClick = { clickedTag ->
                if (inTitle.isEmpty()) {
                    inTitle = clickedTag
                } else {
                    inTitle += " $clickedTag"
                }
            }
        )
    }
}

val mobileTags = listOf(
    "android-studio", "android-jetpack-compose", "xcode", "react-native", "flutter", "material-ui"
)
val databaseTags = listOf(
    "sql", "mysql", "postgresql", "mongodb", "sqlite", "oracle"
)
val testTags = listOf(
    "junit", "selenium", "cypress", "github-actions"
)
val cloudTags = listOf(
    "docker", "kubernetes", "aws", "azure", "firebase", "jenkins", "terraform"
)
val frameworksTags = listOf(
    "angular", "vue.js", "spring", "flask", "django", "express", "laravel", "bootstrap", "tensorflow", "pandas", "numpy"
)
val languageTags = listOf(
    "typescript", "c++", "swift", "ruby", "go", "kotlin", "r", "rust", "scala", "dart", "bash", "objective-c", "c"
)
val allTags = mobileTags + databaseTags + testTags + cloudTags + frameworksTags + languageTags
@Composable
fun SearchContent(
    padding : PaddingValues,
    listState : LazyListState,
    inTitle : String,
    onInTitleChange : (String) -> Unit,
    onSearch : () -> Unit,
    onTagClick : (String) -> Unit
) {
    val shuffledTags = remember { allTags.shuffled() }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .padding(padding)
            .padding(horizontal = 10.dp)
            .fillMaxSize()
    ) {
        item {
            SearchBox(
                value = inTitle,
                onValueChange = onInTitleChange,
                onSearch = onSearch
            )
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            Text(
                text = stringResource(resource = Res.string.popular_tags),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            TagListWrap(
                tags = shuffledTags,
                onTagClick = onTagClick
            )
        }
    }
}

@Composable
fun SearchBox(
    modifier : Modifier = Modifier,
    value : String,
    onValueChange : (String) -> Unit,
    onSearch : () -> Unit
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                contentDescription = null,
                imageVector = Icons.Default.Search
            )
        },
        singleLine = true,
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(onClick = { onValueChange("") }) {
                    Icon(
                        contentDescription = null,
                        imageVector = Icons.Default.Close
                    )
                }
            }
        },
        keyboardActions = KeyboardActions(
            onSearch = { onSearch() }
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        placeholder = { Text(stringResource(resource = Res.string.search_placeholder)) }
    )
}
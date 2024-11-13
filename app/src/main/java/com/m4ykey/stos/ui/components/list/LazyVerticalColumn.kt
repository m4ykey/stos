package com.m4ykey.stos.ui.components.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T: Any> LazyVerticalColumn(
    modifier: Modifier,
    items : LazyPagingItems<T>,
    key : ((item : T) -> Any)? = null,
    contentType : (index : Int) -> Any? = { null },
    onItemContent : @Composable (item : T) -> Unit,
    onLoadingContent : @Composable () -> Unit = { CircularProgressIndicator() },
    onErrorContent : @Composable () -> Unit = { Text(text = "Error loading items") },
    loadThreshold : Int = 20
) {
    val listState = rememberLazyListState()
    val firstVisibleItemIndex = remember { derivedStateOf { listState.firstVisibleItemIndex } }
    val totalItemCount = items.itemCount

    val loadState = items.loadState
    val isEndOfPaginationReached = loadState.append is LoadState.NotLoading &&
            loadState.append.endOfPaginationReached

    LaunchedEffect(firstVisibleItemIndex.value, totalItemCount) {
        if (!isEndOfPaginationReached && firstVisibleItemIndex.value >= totalItemCount - loadThreshold) {
            items.retry()
        }
    }

    CompositionLocalProvider(
        value = LocalOverscrollConfiguration provides null
    ) {
        LazyColumn(
            modifier = modifier,
            state = listState
        ) {
            items(
                count = items.itemCount,
                key = if (key != null) { index -> items[index]?.let { key(it) }!! } else null,
                contentType = contentType
            ) { index ->
                val item = items[index]
                if (item != null) {
                    onItemContent(item)
                }
            }
            when (loadState.append) {
                is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            onLoadingContent()
                        }
                    }
                }
                is LoadState.Error -> {
                    item {
                        Box(
                            modifier = modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            onErrorContent()
                        }
                    }
                }

                else ->  {  }
            }
            when (loadState.refresh) {
                is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            onLoadingContent()
                        }
                    }
                }
                is LoadState.Error -> {
                    item {
                        Box(
                            modifier = modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            onErrorContent()
                        }
                    }
                }
                else -> Unit
            }
        }
    }
}
package com.m4ykey.stos.ui.components.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

@Composable
fun <T: Any> LazyVerticalColumn(
    modifier: Modifier,
    items : LazyPagingItems<T>,
    key : ((item : T) -> Any)? = null,
    contentType : (index : Int) -> Any? = { null },
    onItemContent : @Composable (item : T) -> Unit,
    onLoadingContent : @Composable () -> Unit = { CircularProgressIndicator() },
    onErrorContent : @Composable () -> Unit = { Text(text = "Error loading items") }
) {
    LazyColumn(modifier = modifier) {
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
        when (items.loadState.append) {
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
        when (items.loadState.refresh) {
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
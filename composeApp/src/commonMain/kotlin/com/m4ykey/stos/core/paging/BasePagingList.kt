package com.m4ykey.stos.core.paging

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paging.LoadStateError
import app.cash.paging.LoadStateLoading
import app.cash.paging.LoadStateNotLoading
import app.cash.paging.compose.LazyPagingItems
import com.m4ykey.stos.question.presentation.components.ErrorComponent

val defaultLoading : @Composable () -> Unit = { DefaultLoading() }
val defaultError : @Composable (Throwable?) -> Unit = { DefaultError(it) }
val defaultEmpty : @Composable () -> Unit = { DefaultEmpty() }

@Composable
fun <T : Any> BasePagingList(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    items: LazyPagingItems<T>,
    itemContent: @Composable (item: T) -> Unit,
    contentPadding: PaddingValues = PaddingValues(10.dp),
    showDivider: Boolean = true,
    loadingContent: @Composable () -> Unit = defaultLoading,
    errorContent: @Composable (Throwable?) -> Unit = defaultError,
    emptyContent: @Composable () -> Unit = defaultEmpty,
    itemKey : (T) -> Any = { it.hashCode() }
) {
    when (val refreshState = items.loadState.refresh) {
        is LoadStateLoading -> {
            loadingContent()
        }

        is LoadStateError -> {
            errorContent(refreshState.error)
        }

        is LoadStateNotLoading -> {
            if (items.itemCount == 0) {
                emptyContent()
            } else {
                LazyColumn(
                    modifier = modifier,
                    state = listState,
                    contentPadding = contentPadding
                ) {
                    items(
                        count = items.itemCount,
                        key = { index -> items[index]?.let { itemKey(it) } ?: index },
                        contentType = { "paged_item" }
                    ) { index ->
                        items[index]?.let { item ->
                            itemContent(item)

                            if (showDivider && index < items.itemCount - 1) {
                                HorizontalDivider(modifier = Modifier.padding(horizontal = 5.dp))
                            }
                        }
                    }

                    when (val appendState = items.loadState.append) {
                        is LoadStateLoading -> {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }

                        is LoadStateError -> {
                            item {
                                errorContent(appendState.error)
                            }
                        }

                        is LoadStateNotLoading -> {}
                    }
                }
            }
        }
    }
}

@Composable
fun DefaultLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun DefaultEmpty() {

}

@Composable
fun DefaultError(error: Throwable?) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ErrorComponent(error?.message.toString())
    }
}
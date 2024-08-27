package com.m4ykey.network.core

import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T : Any> launchPaging(
    scope : CoroutineScope,
    source : suspend () -> Flow<PagingData<T>>,
    onDataCollected : (Flow<PagingData<T>>) -> Unit
) {
    scope.launch {
        val flow = source().cachedIn(scope)
        onDataCollected(flow)
    }
}
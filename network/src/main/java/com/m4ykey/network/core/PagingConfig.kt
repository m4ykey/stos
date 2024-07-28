package com.m4ykey.network.core

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow

val pagingConfig = PagingConfig(
    pageSize = 20,
    enablePlaceholders = false
)

fun <T : Any> createPager(pagingSourceFactory : () -> PagingSource<Int, T>) : Flow<PagingData<T>> {
    return Pager(
        config = pagingConfig,
        pagingSourceFactory = pagingSourceFactory
    ).flow
}
package com.m4ykey.network.core

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.m4ykey.network.core.Constants.PAGE_SIZE
import kotlinx.coroutines.flow.Flow

fun <T : Any> createPager(pagingSourceFactory : () -> PagingSource<Int, T>) : Flow<PagingData<T>> {
    return Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false,
            prefetchDistance = 2,
            initialLoadSize = PAGE_SIZE,
            maxSize = PAGE_SIZE * 2
        ),
        pagingSourceFactory = pagingSourceFactory
    ).flow
}
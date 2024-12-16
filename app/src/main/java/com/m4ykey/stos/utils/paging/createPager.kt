package com.m4ykey.stos.utils.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow

fun <T : Any> createPager(
    pagingSourceFactory : () -> PagingSource<Int, T>
) : Flow<PagingData<T>> {
    return Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = pagingSourceFactory
    ).flow
}
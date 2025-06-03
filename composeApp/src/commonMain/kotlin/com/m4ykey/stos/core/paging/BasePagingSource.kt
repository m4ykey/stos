package com.m4ykey.stos.core.paging

import app.cash.paging.PagingSource
import app.cash.paging.PagingState
import app.cash.paging.PagingSourceLoadResult

abstract class BasePagingSource<Value : Any> : PagingSource<Int, Value>() {

    override fun getRefreshKey(state: PagingState<Int, Value>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey
                ?: state.closestPageToPosition(position)?.nextKey
        }
    }

    protected abstract suspend fun loadData(page : Int, pageSize : Int) : Result<List<Value>>

    override suspend fun load(params: LoadParams<Int>): PagingSourceLoadResult<Int, Value> {
        val position = params.key ?: 1
        val pageSize = params.loadSize

        return try {
            val result = loadData(page = position, pageSize = pageSize)

            result.fold(
                onSuccess = { data ->
                    LoadResult.Page(
                        data = data,
                        prevKey = if (position == 1) null else position - 1,
                        nextKey = if (data.size < pageSize) null else position + 1
                    )
                },
                onFailure = { exception ->
                    LoadResult.Error(exception)
                }
            )
        } catch (exception : Exception) {
            LoadResult.Error(exception)
        }
    }
}
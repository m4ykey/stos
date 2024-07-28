package com.m4ykey.network.core

import androidx.paging.PagingSource
import androidx.paging.PagingState

abstract class BasePagingSource<T : Any>(val api : Any) : PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val page = params.key ?: 1

            val data = loadPage(page, params.loadSize)

            val nextKey = if (data.isEmpty()) null else page + 1
            val prevKey = if (page == 1) null else page - 1

            LoadResult.Page(
                nextKey = nextKey,
                prevKey = prevKey,
                data = data
            )
        } catch (e : Exception) {
            LoadResult.Error(e)
        }
    }

    abstract suspend fun loadPage(page : Int, pageSize : Int) : List<T>
}
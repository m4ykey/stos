package com.m4ykey.stos.utils.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import java.io.IOException

abstract class BasePagingSource<T : Any>(val api : Any) : PagingSource<Int, T>() {

    abstract suspend fun loadPage(page : Int, pageSize : Int) : List<T>

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition)
        return anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.plus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val page = params.key ?: 1

            val data = loadPage(page, params.loadSize)

            val (prevKey, nextKey) = getPagingKeys(page, data)

            LoadResult.Page(
                data = data,
                nextKey = nextKey,
                prevKey = prevKey
            )
        } catch (e : IOException) {
            LoadResult.Error(e)
        } catch (e : Exception) {
            LoadResult.Error(e)
        }
    }

    private fun getPagingKeys(page : Int, data : List<T>) : Pair<Int?, Int?> {
        val nextKey = if (data.isEmpty()) null else page + 1
        val prevKey = if (page == 1) null else page - 1
        return Pair(nextKey, prevKey)
    }

}
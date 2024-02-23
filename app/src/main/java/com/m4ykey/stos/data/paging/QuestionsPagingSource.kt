package com.m4ykey.stos.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.m4ykey.stos.data.api.QuestionApi
import com.m4ykey.stos.data.domain.model.question.QuestionItem
import com.m4ykey.stos.data.model.mapper.toQuestionItem
import javax.inject.Inject

class QuestionsPagingSource @Inject constructor(
    private val api : QuestionApi
) : PagingSource<Int, QuestionItem>() {

    override fun getRefreshKey(state: PagingState<Int, QuestionItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.let { closestPage ->
                if (closestPage.prevKey == null) closestPage.nextKey else closestPage.prevKey
            }
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, QuestionItem> {
        return try {
            val page = params.key ?: 1

            val response = api.getQuestions(
                page = page,
                pageSize = params.loadSize
            ).items.map { it.toQuestionItem() }

            LoadResult.Page(
                data = response,
                prevKey = if (page > 1) page - 1 else null,
                nextKey = if (response.isNotEmpty()) page + 1 else null
            )
        } catch (e : Exception) {
            LoadResult.Error(e)
        }
    }
}
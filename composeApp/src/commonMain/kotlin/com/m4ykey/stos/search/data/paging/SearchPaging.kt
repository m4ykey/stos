package com.m4ykey.stos.search.data.paging

import com.m4ykey.stos.core.network.ApiResult
import com.m4ykey.stos.core.network.safeApi
import com.m4ykey.stos.core.paging.BasePagingSource
import com.m4ykey.stos.question.data.mappers.toQuestion
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.search.data.network.RemoteSearchService

class SearchPaging(
    private val service : RemoteSearchService,
    private val inTitle : String?,
    private val tagged : String?
) : BasePagingSource<Question>() {

    override suspend fun loadData(
        page: Int,
        pageSize: Int
    ): Result<List<Question>> {
        return when (val result = safeApi { service.search(
            page = page,
            pageSize = pageSize,
            inTitle = inTitle,
            tagged = tagged.orEmpty(),
        ) }) {
            is ApiResult.Failure -> {
                Result.failure(result.exception)
            }
            is ApiResult.Success -> {
                val questions = result.data.items?.map { it.toQuestion() }!!
                Result.success(questions)
            }
        }
    }
}
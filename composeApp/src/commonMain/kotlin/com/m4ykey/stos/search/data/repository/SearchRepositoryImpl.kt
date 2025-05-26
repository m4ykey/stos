package com.m4ykey.stos.search.data.repository

import com.m4ykey.stos.core.network.ApiResult
import com.m4ykey.stos.core.network.safeApi
import com.m4ykey.stos.question.data.mappers.toQuestion
import com.m4ykey.stos.question.data.network.model.Items
import com.m4ykey.stos.question.data.network.model.QuestionDto
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.search.data.network.RemoteSearchService
import com.m4ykey.stos.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchRepositoryImpl(
    private val remoteSearchService: RemoteSearchService
) : SearchRepository {

    override suspend fun search(
        page: Int,
        pageSize: Int,
        sort: String,
        inTitle: String,
        tagged: String,
        order : String
    ): Flow<ApiResult<List<Question>>> = flow {
        val result = safeApi<Items<QuestionDto>> {
            remoteSearchService
                .search(
                    page = page,
                    pageSize = pageSize,
                    order = order,
                    inTitle = inTitle,
                    sort = sort,
                    tagged = tagged
                )
        }

        when (result) {
            is ApiResult.Success -> {
                val questions = result.data.items?.map { it.toQuestion() }.orEmpty()
                emit(ApiResult.Success(questions))
            }
            is ApiResult.Failure -> emit(ApiResult.Failure(result.exception))
        }
    }
}
package com.m4ykey.stos.question.data.paging

import com.m4ykey.stos.core.network.ApiResult
import com.m4ykey.stos.core.network.safeApi
import com.m4ykey.stos.core.paging.BasePagingSource
import com.m4ykey.stos.question.data.mappers.toQuestion
import com.m4ykey.stos.question.data.network.RemoteQuestionService
import com.m4ykey.stos.question.domain.model.Question

class QuestionTagPaging(
    private val service : RemoteQuestionService,
    private val tag : String
) : BasePagingSource<Question>() {

    override suspend fun loadData(
        page: Int,
        pageSize: Int
    ): Result<List<Question>> {
        return when (val result = safeApi { service.getQuestionByTag(page, pageSize, tagged = tag) }) {
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
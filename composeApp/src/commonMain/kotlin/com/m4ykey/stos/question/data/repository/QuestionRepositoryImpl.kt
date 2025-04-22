package com.m4ykey.stos.question.data.repository

import com.m4ykey.stos.core.network.ApiResult
import com.m4ykey.stos.core.network.safeApi
import com.m4ykey.stos.question.data.mappers.toQuestion
import com.m4ykey.stos.question.data.network.RemoteQuestionService
import com.m4ykey.stos.question.data.network.model.Items
import com.m4ykey.stos.question.data.network.model.QuestionDto
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.question.domain.repository.QuestionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class QuestionRepositoryImpl(
    private val remoteQuestionService: RemoteQuestionService
) : QuestionRepository {

    override suspend fun getQuestions(
        page: Int,
        pageSize: Int,
        sort: String
    ): Flow<ApiResult<List<Question>>> = flow {
        val result = safeApi<Items<QuestionDto>> {
            remoteQuestionService
                .getQuestions(page = page, pageSize = pageSize, sort = sort)
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
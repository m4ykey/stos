package com.m4ykey.stos.question.data.repository

import com.m4ykey.stos.core.network.ApiResult
import com.m4ykey.stos.core.network.safeApi
import com.m4ykey.stos.question.data.mappers.toAnswer
import com.m4ykey.stos.question.data.mappers.toQuestion
import com.m4ykey.stos.question.data.mappers.toQuestionDetail
import com.m4ykey.stos.question.data.network.RemoteQuestionService
import com.m4ykey.stos.question.data.network.model.AnswerDto
import com.m4ykey.stos.question.data.network.model.Items
import com.m4ykey.stos.question.data.network.model.QuestionDetailDto
import com.m4ykey.stos.question.data.network.model.QuestionDto
import com.m4ykey.stos.question.domain.model.Answer
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.question.domain.model.QuestionDetail
import com.m4ykey.stos.question.domain.repository.QuestionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class QuestionRepositoryImpl(
    private val remoteQuestionService: RemoteQuestionService
) : QuestionRepository {

    override suspend fun getQuestionAnswer(id: Int): Flow<ApiResult<List<Answer>>> = flow {
        val result = safeApi<Items<AnswerDto>> {
            remoteQuestionService.getQuestionAnswers(id = id)
        }

        when (result) {
            is ApiResult.Success -> {
                val answers = result.data.items?.map { it.toAnswer() }.orEmpty()
                emit(ApiResult.Success(answers))
            }
            is ApiResult.Failure -> emit(ApiResult.Failure(result.exception))
        }
    }

    override suspend fun getQuestionByTag(
        tag: String,
        page: Int,
        pageSize: Int,
        sort: String
    ): Flow<ApiResult<List<Question>>> = flow {
        val result = safeApi<Items<QuestionDto>> {
            remoteQuestionService
                .getQuestionByTag(page = page, pageSize = pageSize, sort = sort, tagged = tag)
        }

        when (result) {
            is ApiResult.Failure -> emit(ApiResult.Failure(result.exception))
            is ApiResult.Success -> {
                val questions = result.data.items?.map { it.toQuestion() }.orEmpty()
                emit(ApiResult.Success(questions))
            }
        }
    }

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

    override suspend fun getQuestionById(id: Int): Flow<ApiResult<QuestionDetail>> = flow {
        val result = safeApi<Items<QuestionDetailDto>> {
            remoteQuestionService
                .getQuestionById(id = id)
        }

        when (result) {
            is ApiResult.Failure -> emit(ApiResult.Failure(result.exception))
            is ApiResult.Success -> {
                val questions = result.data.items?.map { it.toQuestionDetail() }?.firstOrNull()
                if (questions != null) {
                    emit(ApiResult.Success(questions))
                }
            }
        }
    }
}
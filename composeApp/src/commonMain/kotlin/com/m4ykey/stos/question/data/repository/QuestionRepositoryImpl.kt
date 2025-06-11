package com.m4ykey.stos.question.data.repository

import app.cash.paging.Pager
import app.cash.paging.PagingData
import com.m4ykey.stos.core.network.ApiResult
import com.m4ykey.stos.core.network.safeApi
import com.m4ykey.stos.core.paging.pagingConfig
import com.m4ykey.stos.question.data.mappers.toAnswer
import com.m4ykey.stos.question.data.mappers.toQuestionDetail
import com.m4ykey.stos.question.data.network.RemoteQuestionService
import com.m4ykey.stos.question.data.network.model.QuestionAnswerDto
import com.m4ykey.stos.core.model.Items
import com.m4ykey.stos.question.data.network.model.QuestionDetailDto
import com.m4ykey.stos.question.data.paging.QuestionPaging
import com.m4ykey.stos.question.data.paging.QuestionTagPaging
import com.m4ykey.stos.question.domain.model.QuestionAnswer
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.question.domain.model.QuestionDetail
import com.m4ykey.stos.question.domain.repository.QuestionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class QuestionRepositoryImpl(
    private val remoteQuestionService: RemoteQuestionService,
    private val dispatcherIO : CoroutineDispatcher
) : QuestionRepository {

    override suspend fun getQuestionAnswer(id: Int): Flow<ApiResult<List<QuestionAnswer>>> = flow {
        val result = safeApi<Items<QuestionAnswerDto>> {
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
    ): Flow<PagingData<Question>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                QuestionTagPaging(service = remoteQuestionService, tag = tag)
            }
        ).flow.flowOn(dispatcherIO)
    }

    override suspend fun getQuestions(
        page: Int,
        pageSize: Int,
        sort: String
    ): Flow<PagingData<Question>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                QuestionPaging(service = remoteQuestionService)
            }
        ).flow.flowOn(dispatcherIO)
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
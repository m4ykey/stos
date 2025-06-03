package com.m4ykey.stos.question.domain.use_case

import app.cash.paging.PagingData
import app.cash.paging.PagingSource
import com.m4ykey.stos.core.network.ApiResult
import com.m4ykey.stos.question.domain.model.Answer
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.question.domain.model.QuestionDetail
import com.m4ykey.stos.question.domain.repository.QuestionRepository
import kotlinx.coroutines.flow.Flow

class QuestionUseCase(
    private val repository: QuestionRepository
) {

    suspend fun getQuestionAnswer(
        id : Int
    ) : Flow<ApiResult<List<Answer>>> {
        return repository.getQuestionAnswer(id)
    }

    suspend fun getQuestionByTag(
        page : Int,
        pageSize: Int,
        sort: String,
        tag : String
    ) : Flow<PagingData<Question>> {
        return repository.getQuestionByTag(tag, page, pageSize, sort)
    }

    suspend fun getQuestions(
        page : Int,
        pageSize : Int,
        sort : String
    ) : Flow<PagingData<Question>> {
        return repository.getQuestions(page, pageSize, sort)
    }

    suspend fun getQuestionById(id : Int) : Flow<ApiResult<QuestionDetail>> {
        return repository.getQuestionById(id)
    }

}
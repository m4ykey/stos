package com.m4ykey.stos.question.domain.use_case

import com.m4ykey.stos.core.network.ApiResult
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.question.domain.model.QuestionDetail
import com.m4ykey.stos.question.domain.repository.QuestionRepository
import kotlinx.coroutines.flow.Flow

class QuestionUseCase(
    private val repository: QuestionRepository
) {

    suspend fun getQuestions(
        page : Int,
        pageSize : Int,
        sort : String
    ) : Flow<ApiResult<List<Question>>> {
        return repository.getQuestions(page, pageSize, sort)
    }

    suspend fun getQuestionById(id : Int) : Flow<ApiResult<QuestionDetail>> {
        return repository.getQuestionById(id)
    }

}
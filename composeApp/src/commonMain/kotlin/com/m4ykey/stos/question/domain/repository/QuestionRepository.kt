package com.m4ykey.stos.question.domain.repository

import com.m4ykey.stos.core.network.ApiResult
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.question.domain.model.QuestionDetail
import kotlinx.coroutines.flow.Flow

interface QuestionRepository {

    suspend fun getQuestions(page : Int, pageSize : Int, sort : String) : Flow<ApiResult<List<Question>>>
    suspend fun getQuestionById(id : Int) : Flow<ApiResult<QuestionDetail>>
    suspend fun getQuestionByTag(tag : String, page : Int, pageSize: Int, sort: String) : Flow<ApiResult<List<Question>>>

}
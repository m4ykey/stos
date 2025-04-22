package com.m4ykey.stos.question.domain.repository

import com.m4ykey.stos.core.network.ApiResult
import com.m4ykey.stos.question.domain.model.Question
import kotlinx.coroutines.flow.Flow

interface QuestionRepository {

    suspend fun getQuestions(page : Int, pageSize : Int, sort : String) : Flow<ApiResult<List<Question>>>

}
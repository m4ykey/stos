package com.m4ykey.stos.question.domain.repository

import app.cash.paging.PagingData
import com.m4ykey.stos.core.network.ApiResult
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.question.domain.model.QuestionAnswer
import com.m4ykey.stos.question.domain.model.QuestionDetail
import kotlinx.coroutines.flow.Flow

interface QuestionRepository {

    fun getQuestions(page : Int, pageSize : Int, sort : String) : Flow<PagingData<Question>>
    fun getQuestionById(id : Int) : Flow<ApiResult<QuestionDetail>>
    fun getQuestionByTag(tag : String, page : Int, pageSize: Int, sort: String) : Flow<PagingData<Question>>
    fun getQuestionAnswer(id : Int) : Flow<ApiResult<List<QuestionAnswer>>>

}
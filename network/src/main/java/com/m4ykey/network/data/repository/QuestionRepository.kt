package com.m4ykey.network.data.repository

import androidx.paging.PagingData
import com.m4ykey.network.data.model.Question
import com.m4ykey.network.data.model.QuestionDetail
import kotlinx.coroutines.flow.Flow

interface QuestionRepository {

    suspend fun getQuestions(sort : String) : Flow<PagingData<Question>>
    suspend fun getQuestionDetail(questionId : Int) : Flow<QuestionDetail>
    suspend fun getQuestionTag(tag : String, sort : String) : Flow<PagingData<Question>>

}
package com.m4ykey.network.data.repository

import androidx.paging.PagingData
import com.m4ykey.network.data.model.Question
import kotlinx.coroutines.flow.Flow

interface QuestionRepository {

    suspend fun getQuestions(sort : String) : Flow<PagingData<Question>>

}
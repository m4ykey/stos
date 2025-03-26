package com.m4ykey.stos.data.domain.repository

import com.m4ykey.stos.data.domain.model.Question
import kotlinx.coroutines.flow.Flow

interface QuestionRepository {

    suspend fun getQuestions(page : Int, pageSize : Int, sort : String) : Flow<List<Question>>

}
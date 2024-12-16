package com.m4ykey.stos.data.repository

import androidx.paging.PagingData
import com.m4ykey.stos.domain.models.questions.Question
import kotlinx.coroutines.flow.Flow

interface QuestionRepository {

    fun getQuestions(sort : String, order : String) : Flow<PagingData<Question>>

}
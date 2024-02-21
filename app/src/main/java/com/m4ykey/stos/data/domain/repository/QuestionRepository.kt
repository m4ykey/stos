package com.m4ykey.stos.data.domain.repository

import androidx.paging.PagingData
import com.m4ykey.stos.data.domain.model.question.QuestionItem
import kotlinx.coroutines.flow.Flow

interface QuestionRepository {

    suspend fun getQuestions(
        page : Int,
        pageSize : Int,
        sort : String
    ) : Flow<PagingData<QuestionItem>>

}
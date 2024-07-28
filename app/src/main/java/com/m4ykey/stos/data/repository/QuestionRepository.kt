package com.m4ykey.stos.data.repository

import androidx.paging.PagingData
import com.m4ykey.network.core.createPager
import com.m4ykey.network.data.model.Question
import com.m4ykey.network.paging.QuestionPagingSource
import com.m4ykey.network.service.QuestionService
import kotlinx.coroutines.flow.Flow

class QuestionRepository(private val service : QuestionService) {

    suspend fun getQuestions() : Flow<PagingData<Question>> = createPager {
        QuestionPagingSource(service)
    }
}
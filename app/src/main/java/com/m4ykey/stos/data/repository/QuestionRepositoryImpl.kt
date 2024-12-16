package com.m4ykey.stos.data.repository

import androidx.paging.PagingData
import com.m4ykey.stos.data.network.service.QuestionService
import com.m4ykey.stos.data.paging.QuestionPagingSource
import com.m4ykey.stos.domain.models.questions.Question
import com.m4ykey.stos.utils.paging.createPager
import kotlinx.coroutines.flow.Flow

class QuestionRepositoryImpl(
    private val service : QuestionService
) : QuestionRepository {

    override fun getQuestions(sort : String, order : String): Flow<PagingData<Question>> = createPager {
        QuestionPagingSource(
            order = order,
            sort = sort,
            service = service
        )
    }
}
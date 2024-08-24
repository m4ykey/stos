package com.m4ykey.stos.data.repository

import androidx.paging.PagingData
import com.m4ykey.network.core.createPager
import com.m4ykey.network.core.safeApiCall
import com.m4ykey.network.data.model.Question
import com.m4ykey.network.data.model.QuestionDetail
import com.m4ykey.network.data.repository.QuestionRepository
import com.m4ykey.network.data.toQuestionDetail
import com.m4ykey.network.paging.QuestionPagingSource
import com.m4ykey.network.paging.QuestionTagPagingSource
import com.m4ykey.network.service.QuestionService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class QuestionRepositoryImpl(private val service : QuestionService) : QuestionRepository {

    override suspend fun getQuestions(sort  : String) : Flow<PagingData<Question>> = createPager {
        QuestionPagingSource(service, sort)
    }

    override suspend fun getQuestionDetail(questionId: Int): Flow<QuestionDetail> = flow {
        val result = safeApiCall {
            service.getQuestionDetail(questionId = questionId).items.map { it.toQuestionDetail() }.first()
        }
        if (result.isSuccess) {
            emit(result.getOrThrow())
        } else {
            throw result.exceptionOrNull() ?: Exception("Unknown error")
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getQuestionTag(tag: String, sort : String): Flow<PagingData<Question>> = createPager {
        QuestionTagPagingSource(service = service, tag = tag, sort = sort)
    }
}
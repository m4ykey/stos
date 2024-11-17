package com.m4ykey.stos.data.repository

import androidx.paging.PagingData
import com.m4ykey.network.core.createPager
import com.m4ykey.network.core.safeApiCall
import com.m4ykey.network.data.model.Answer
import com.m4ykey.network.data.model.Comment
import com.m4ykey.network.data.model.Question
import com.m4ykey.network.data.model.QuestionDetail
import com.m4ykey.network.data.repository.QuestionRepository
import com.m4ykey.network.data.toQuestionDetail
import com.m4ykey.network.paging.question.QuestionAnswerPagingSource
import com.m4ykey.network.paging.question.QuestionCommentPagingSource
import com.m4ykey.network.paging.question.QuestionPagingSource
import com.m4ykey.network.paging.question.QuestionTagPagingSource
import com.m4ykey.network.service.QuestionService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class QuestionRepositoryImpl(private val service : QuestionService) : QuestionRepository {

    override suspend fun getQuestionComment(questionId: Int): Flow<PagingData<Comment>> = flow<PagingData<Comment>> {
        QuestionCommentPagingSource(questionId = questionId, service = service)
    }.flowOn(Dispatchers.IO)

    override suspend fun getQuestions(sort  : String) : Flow<PagingData<Question>> = createPager {
        QuestionPagingSource(service, sort)
    }.flowOn(Dispatchers.IO)

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
    }.flowOn(Dispatchers.IO)

    override suspend fun getQuestionAnswer(questionId: Int): Flow<PagingData<Answer>> = createPager {
        QuestionAnswerPagingSource(service = service, questionId = questionId)
    }.flowOn(Dispatchers.IO)
}
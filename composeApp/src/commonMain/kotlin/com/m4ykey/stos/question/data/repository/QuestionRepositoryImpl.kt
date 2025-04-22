package com.m4ykey.stos.question.data.repository

import com.m4ykey.stos.question.data.mappers.toQuestion
import com.m4ykey.stos.question.data.network.RemoteQuestionService
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.question.domain.repository.QuestionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class QuestionRepositoryImpl(
    private val remoteQuestionService: RemoteQuestionService
) : QuestionRepository {

    override suspend fun getQuestions(
        page: Int,
        pageSize: Int,
        sort: String
    ): Flow<List<Question>> = flow {
        remoteQuestionService
            .getQuestions(page = page, pageSize = pageSize, sort = sort).items
            ?.map { dto ->
                dto.toQuestion()
            }
    }
}
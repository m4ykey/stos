package com.m4ykey.stos.data.paging

import com.m4ykey.stos.data.mapper.toQuestion
import com.m4ykey.stos.data.network.service.QuestionService
import com.m4ykey.stos.domain.models.questions.Question
import com.m4ykey.stos.utils.paging.BasePagingSource

class QuestionPagingSource(
    private val service : QuestionService,
    private val order : String,
    private val sort : String
) : BasePagingSource<Question>(service) {

    override suspend fun loadPage(page: Int, pageSize: Int): List<Question> {
        val response = service.getQuestions(
            page = page,
            pageSize = pageSize,
            sort = sort,
            order = order
        )
        return response.items?.map { it.toQuestion() }!!
    }
}
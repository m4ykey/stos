package com.m4ykey.network.paging.question

import com.m4ykey.network.core.BasePagingSource
import com.m4ykey.network.data.model.Question
import com.m4ykey.network.data.toQuestion
import com.m4ykey.network.service.QuestionService

class QuestionPagingSource(
    private val service: QuestionService,
    private val sort : String
) : BasePagingSource<Question>(service) {

    override suspend fun loadPage(page: Int, pageSize: Int): List<Question> {
        val response = service.getQuestions(
            sort = sort,
            page = page,
            pageSize = pageSize
        )
        return response.items.map { it.toQuestion() }
    }
}
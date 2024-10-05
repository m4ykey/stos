package com.m4ykey.network.paging.question

import com.m4ykey.network.core.BasePagingSource
import com.m4ykey.network.data.model.Question
import com.m4ykey.network.data.toQuestion
import com.m4ykey.network.service.QuestionService

class QuestionTagPagingSource(
    private val tag : String,
    private val sort : String,
    private val service : QuestionService
) : BasePagingSource<Question>(service) {

    override suspend fun loadPage(page: Int, pageSize: Int): List<Question> {
        val response = service.getQuestionTag(
            tag = tag,
            sort = sort,
            page = page,
            pageSize = pageSize
        )
        return response.items.map { it.toQuestion() }
    }
}
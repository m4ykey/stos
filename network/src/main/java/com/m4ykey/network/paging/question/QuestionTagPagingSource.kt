package com.m4ykey.network.paging.question

import com.m4ykey.network.core.BasePagingSource
import com.m4ykey.network.core.Constants.MAX_RETRIES
import com.m4ykey.network.core.Constants.RETRY_DELAY_MS
import com.m4ykey.network.data.model.Question
import com.m4ykey.network.data.toQuestion
import com.m4ykey.network.service.question.QuestionService
import kotlinx.coroutines.delay

class QuestionTagPagingSource(
    private val tag : String,
    private val sort : String,
    private val service : QuestionService
) : BasePagingSource<Question>(service) {

    override suspend fun loadPage(page: Int, pageSize: Int): List<Question> {
        repeat(MAX_RETRIES) { attempt ->
            try {
                val response = service.getQuestionTag(
                    page = page,
                    pageSize = pageSize,
                    sort = sort,
                    tag = tag
                )
                return response.items.map { it.toQuestion() }
            } catch (e : Exception) {
                if (attempt == MAX_RETRIES - 1) throw e
                delay(RETRY_DELAY_MS)
            }
        }
        throw IllegalStateException("Unreachable code")
    }
}
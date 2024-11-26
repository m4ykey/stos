package com.m4ykey.network.paging.question

import com.m4ykey.network.core.BasePagingSource
import com.m4ykey.network.core.Constants.MAX_RETRIES
import com.m4ykey.network.core.Constants.RETRY_DELAY_MS
import com.m4ykey.network.data.model.Answer
import com.m4ykey.network.data.toAnswer
import com.m4ykey.network.service.question.QuestionService
import kotlinx.coroutines.delay

class QuestionAnswerPagingSource(
    private val service : QuestionService,
    private val questionId : Int
) : BasePagingSource<Answer>(service) {

    override suspend fun loadPage(page: Int, pageSize: Int): List<Answer> {
        repeat(MAX_RETRIES) { attempt ->
            try {
                val response = service.getQuestionAnswer(
                    page = page,
                    pageSize = pageSize,
                    questionId = questionId
                )
                return response.items.map { it.toAnswer() }
            } catch (e : Exception) {
                if (attempt == MAX_RETRIES - 1) throw e
                delay(RETRY_DELAY_MS)
            }
        }
        throw IllegalStateException("Unreachable code")
    }
}
package com.m4ykey.network.paging.question

import com.m4ykey.network.core.BasePagingSource
import com.m4ykey.network.data.model.Answer
import com.m4ykey.network.data.toAnswer
import com.m4ykey.network.service.QuestionService
import kotlinx.coroutines.delay

class QuestionAnswerPagingSource(
    private val service : QuestionService,
    private val questionId : Int
) : BasePagingSource<Answer>(service) {

    override suspend fun loadPage(page: Int, pageSize: Int): List<Answer> {
        val response = service.getQuestionAnswer(
            questionId = questionId,
            page = page,
            pageSize = pageSize
        )

        response.backoff?.takeIf { it > 0}?.let {
            delay(it * 1000L)
        }

        return response.items.map { it.toAnswer() }
    }
}
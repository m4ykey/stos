package com.m4ykey.network.paging.question

import com.m4ykey.network.core.BasePagingSource
import com.m4ykey.network.data.model.Comment
import com.m4ykey.network.data.toComment
import com.m4ykey.network.service.QuestionService
import kotlinx.coroutines.delay

class QuestionCommentPagingSource(
    private val service : QuestionService,
    private val questionId : Int
) : BasePagingSource<Comment>(service) {

    override suspend fun loadPage(page: Int, pageSize: Int): List<Comment> {
        val response = service.getQuestionsComment(
            page = page,
            pageSize = pageSize,
            questionId = questionId
        )

        response.backoff?.let {
            delay(it * 1000L)
        }

        return response.items.map { it.toComment() }
    }
}
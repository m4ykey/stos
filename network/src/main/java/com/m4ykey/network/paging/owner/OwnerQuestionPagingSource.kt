package com.m4ykey.network.paging.owner

import com.m4ykey.network.core.BasePagingSource
import com.m4ykey.network.data.model.Question
import com.m4ykey.network.data.toQuestion
import com.m4ykey.network.service.OwnerService
import kotlinx.coroutines.delay

class OwnerQuestionPagingSource(
    private val service : OwnerService,
    private val ownerId : Int
) : BasePagingSource<Question>(service) {

    override suspend fun loadPage(page: Int, pageSize: Int): List<Question> {
        val response = service.getOwnerQuestions(
            page = page,
            pageSize = pageSize,
            ownerId = ownerId
        )

        response.backoff?.let {
            delay(it * 1000L)
        }

        return response.items.map { it.toQuestion() }
    }
}
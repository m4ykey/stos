package com.m4ykey.network.paging.owner

import com.m4ykey.network.core.BasePagingSource
import com.m4ykey.network.data.model.Answer
import com.m4ykey.network.data.toAnswer
import com.m4ykey.network.service.OwnerService

class OwnerAnswerPagingSource(
    private val service: OwnerService,
    private val ownerId : Int
) : BasePagingSource<Answer>(service) {

    override suspend fun loadPage(page: Int, pageSize: Int): List<Answer> {
        val response = service.getOwnerAnswers(
            page = page,
            pageSize = pageSize,
            ownerId = ownerId
        )
        return response.items.map { it.toAnswer() }
    }
}
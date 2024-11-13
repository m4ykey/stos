package com.m4ykey.network.paging.search

import com.m4ykey.network.core.BasePagingSource
import com.m4ykey.network.data.model.Question
import com.m4ykey.network.data.toQuestion
import com.m4ykey.network.service.SearchService
import kotlinx.coroutines.delay

class SearchPagingSource(
    private val service : SearchService,
    private val inTitle : String? = null,
    private val tagged : String? = null
) : BasePagingSource<Question>(service) {

    override suspend fun loadPage(page: Int, pageSize: Int): List<Question> {
        val response = service.searchQuestions(
            page = page,
            pageSize = pageSize,
            inTitle = inTitle,
            tagged = tagged
        )

        response.backoff?.let {
            delay(it * 1000L)
        }

        return response.items.map { it.toQuestion() }
    }
}
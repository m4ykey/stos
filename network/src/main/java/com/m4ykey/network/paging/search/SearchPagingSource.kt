package com.m4ykey.network.paging.search

import com.m4ykey.network.core.BasePagingSource
import com.m4ykey.network.core.Constants.MAX_RETRIES
import com.m4ykey.network.core.Constants.RETRY_DELAY_MS
import com.m4ykey.network.data.model.Question
import com.m4ykey.network.data.toQuestion
import com.m4ykey.network.service.search.SearchService
import kotlinx.coroutines.delay

class SearchPagingSource(
    private val service : SearchService,
    private val inTitle : String? = null,
    private val tagged : String? = null
) : BasePagingSource<Question>(service) {

    override suspend fun loadPage(page: Int, pageSize: Int): List<Question> {
        repeat(MAX_RETRIES) { attempt ->
            try {
                val response = service.searchQuestions(
                    page = page,
                    pageSize = pageSize,
                    inTitle = inTitle,
                    tagged = tagged
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
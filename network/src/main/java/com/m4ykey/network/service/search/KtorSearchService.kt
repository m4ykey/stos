package com.m4ykey.network.service.search

import com.m4ykey.network.core.withRetry
import com.m4ykey.network.data.Items
import com.m4ykey.network.data.dto.QuestionDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

class KtorSearchService(
    private val client: HttpClient
) : SearchService {

    override suspend fun searchQuestions(
        page : Int,
        pageSize : Int,
        filter : String,
        inTitle : String?,
        site : String,
        sort : String,
        tagged : String?
    ) : Items<QuestionDto> {
        return withRetry {
            client.get {
                url("search")
                parameter("page", page)
                parameter("pagesize", pageSize)
                parameter("filter", filter)
                parameter("site", site)
                parameter("sort", sort)

                inTitle?.let { parameter("intitle", it) }
                tagged?.let { parameter("tagged", it) }
            }.body()
        }
    }
}
package com.m4ykey.network.service

import com.m4ykey.network.core.Constants.DEFAULT_FILTER
import com.m4ykey.network.core.Constants.PAGE
import com.m4ykey.network.core.Constants.PAGE_SIZE
import com.m4ykey.network.core.Constants.SITE
import com.m4ykey.network.core.withRetry
import com.m4ykey.network.data.Items
import com.m4ykey.network.data.dto.QuestionDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

class SearchService(private val client: HttpClient) {

    suspend fun searchQuestions(
        page : Int = PAGE,
        pageSize : Int = PAGE_SIZE,
        filter : String = DEFAULT_FILTER,
        inTitle : String? = null,
        site : String = SITE,
        sort : String = "activity",
        tagged : String? = null
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
package com.m4ykey.stos.search.data.network

import com.m4ykey.stos.core.network.setParameters
import com.m4ykey.stos.core.model.Items
import com.m4ykey.stos.question.data.network.model.QuestionDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments

class SearchService(
    private val client : HttpClient
) : RemoteSearchService {

    override suspend fun search(
        page: Int,
        pageSize: Int,
        filter: String,
        inTitle: String?,
        sort: String,
        tagged: String,
        order: String
    ): Items<QuestionDto> {
        return client.get {
            url {
                appendPathSegments("search")
                setParameters(
                    "page" to page,
                    "pagesize" to pageSize,
                    "filter" to filter,
                    "order" to order,
                    "sort" to sort
                )
                inTitle?.takeIf { it.isNotBlank() }?.let {
                    parameters.append("intitle", it)
                }
                if (tagged.isNotBlank()) {
                    parameters.append("tagged", tagged)
                }
            }
        }.body()
    }
}
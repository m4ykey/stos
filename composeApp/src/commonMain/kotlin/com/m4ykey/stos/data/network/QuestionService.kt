package com.m4ykey.stos.data.network

import com.m4ykey.stos.data.network.Filters.QUESTION_FILTER
import com.m4ykey.stos.data.network.model.Items
import com.m4ykey.stos.data.network.model.QuestionDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments

class QuestionService(private val client : HttpClient) {

    suspend fun getQuestions(
        site : String = "stackoverflow",
        page : Int,
        pageSize : Int,
        filter : String = QUESTION_FILTER,
        sort : String = "hot"
    ) : Items<QuestionDto> {
        return client.get {
            url {
                appendPathSegments("questions")
                parameters.append("site", site)
                parameters.append("page", page.toString())
                parameters.append("pagesize", pageSize.toString())
                parameters.append("sort", sort)
                parameters.append("filter", filter)
            }
        }.body()
    }
}
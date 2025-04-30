package com.m4ykey.stos.question.data.network

import com.m4ykey.stos.question.data.network.model.Items
import com.m4ykey.stos.question.data.network.model.QuestionDetailDto
import com.m4ykey.stos.question.data.network.model.QuestionDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments

class QuestionService(
    private val client : HttpClient
) : RemoteQuestionService {

    override suspend fun getQuestions(
        site: String,
        page: Int,
        pageSize: Int,
        filter: String,
        sort: String
    ): Items<QuestionDto> {
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

    override suspend fun getQuestionById(
        site: String,
        filter: String,
        id : Int
    ): Items<QuestionDetailDto> {
        return client.get {
            url {
                appendPathSegments("questions/$id")
                parameters.append("site", site)
                parameters.append("filter", filter)
            }
        }.body()
    }
}
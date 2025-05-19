package com.m4ykey.stos.question.data.network

import com.m4ykey.stos.core.network.setParameters
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

    override suspend fun getQuestionByTag(
        page: Int,
        pageSize: Int,
        filter: String,
        tagged: String,
        order: String,
        sort: String
    ): Items<QuestionDto> {
        return client.get {
            url {
                appendPathSegments("questions")
                setParameters(
                    "page" to page,
                    "pagesize" to pageSize,
                    "filter" to filter,
                    "tagged" to tagged,
                    "order" to order,
                    "sort" to sort
                )
            }
        }.body()
    }

    override suspend fun getQuestions(
        page: Int,
        pageSize: Int,
        filter: String,
        sort: String,
        order : String
    ): Items<QuestionDto> {
        return client.get {
            url {
                appendPathSegments("questions")
                setParameters(
                    "page" to page,
                    "pagesize" to pageSize,
                    "sort" to sort,
                    "order" to order,
                    "filter" to filter
                )
            }
        }.body()
    }

    override suspend fun getQuestionById(
        filter: String,
        id : Int
    ): Items<QuestionDetailDto> {
        return client.get {
            url {
                appendPathSegments("questions/$id")
                setParameters(
                    "filter" to filter
                )
            }
        }.body()
    }
}
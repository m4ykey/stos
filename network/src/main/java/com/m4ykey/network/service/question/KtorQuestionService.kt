package com.m4ykey.network.service.question

import com.m4ykey.network.core.withRetry
import com.m4ykey.network.data.Items
import com.m4ykey.network.data.dto.AnswerDto
import com.m4ykey.network.data.dto.QuestionDetailDto
import com.m4ykey.network.data.dto.QuestionDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

class KtorQuestionService(
    private val client : HttpClient
) : QuestionService {

    override suspend fun getQuestions(
        filter : String,
        sort : String,
        page : Int,
        pageSize : Int,
        site : String,
        order : String
    ) : Items<QuestionDto> {
        return withRetry {
            client.get {
                url("questions")
                parameter("site", site)
                parameter("page", page)
                parameter("pagesize", pageSize)
                parameter("filter", filter)
                parameter("sort", sort)
                parameter("order", order)
            }.body()
        }
    }

    override suspend fun getQuestionDetail(
        filter: String,
        questionId : Int,
        site : String
    ) : Items<QuestionDetailDto> {
        return withRetry {
            client.get {
                url("questions/$questionId")
                parameter("site", site)
                parameter("filter", filter)
            }.body()
        }
    }

    override suspend fun getQuestionTag(
        filter: String,
        tag: String,
        sort: String,
        page: Int,
        site : String,
        order : String,
        pageSize : Int
    ) : Items<QuestionDto> {
        return withRetry {
            client.get {
                url("questions")
                parameter("site", site)
                parameter("page", page)
                parameter("pagesize", pageSize)
                parameter("filter", filter)
                parameter("order", order)
                parameter("sort", sort)
                parameter("tagged", tag)
            }.body()
        }
    }

    override suspend fun getQuestionAnswer(
        questionId : Int,
        site : String,
        page : Int,
        pageSize: Int,
        filter: String
    ) : Items<AnswerDto> {
        return withRetry {
            client.get {
                url("questions/$questionId/answers")
                parameter("site", site)
                parameter("page", page)
                parameter("pagesize", pageSize)
                parameter("filter", filter)
            }.body()
        }
    }
}
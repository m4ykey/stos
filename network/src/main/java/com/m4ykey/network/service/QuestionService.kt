package com.m4ykey.network.service

import com.m4ykey.network.core.Constants.ANSWER_FILTER
import com.m4ykey.network.core.Constants.DEFAULT_FILTER
import com.m4ykey.network.core.Constants.DETAIL_FILTER
import com.m4ykey.network.core.Constants.PAGE
import com.m4ykey.network.core.Constants.PAGE_SIZE
import com.m4ykey.network.core.Constants.SITE
import com.m4ykey.network.data.Items
import com.m4ykey.network.data.dto.AnswerDto
import com.m4ykey.network.data.dto.QuestionDetailDto
import com.m4ykey.network.data.dto.QuestionDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

class QuestionService(private val client : HttpClient) {

    suspend fun getQuestions(
        filter : String = DEFAULT_FILTER,
        sort : String,
        page : Int = PAGE,
        pageSize : Int = PAGE_SIZE,
        site : String = SITE,
        order : String = "desc"
    ) : Items<QuestionDto> {
        return client.get {
            url("questions")
            parameter("site", site)
            parameter("page", page)
            parameter("pagesize", pageSize)
            parameter("filter", filter)
            parameter("sort", sort)
            parameter("order", order)
        }.body()
    }

    suspend fun getQuestionDetail(
        filter: String = DETAIL_FILTER,
        questionId : Int,
        site : String = SITE
    ) : Items<QuestionDetailDto> {
        return client.get {
            url("questions/$questionId")
            parameter("site", site)
            parameter("filter", filter)
        }.body()
    }

    suspend fun getQuestionTag(
        filter: String = DEFAULT_FILTER,
        tag: String,
        sort: String,
        page: Int = PAGE,
        site : String = SITE,
        order : String = "desc",
        pageSize : Int = PAGE_SIZE
    ) : Items<QuestionDto> {
        return client.get {
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

    suspend fun getQuestionAnswer(
        questionId : Int,
        site : String = SITE,
        page : Int = PAGE,
        pageSize: Int = PAGE_SIZE,
        filter: String = ANSWER_FILTER
    ) : Items<AnswerDto> {
        return client.get {
            url("questions/$questionId/answers")
            parameter("site", site)
            parameter("page", page)
            parameter("pagesize", pageSize)
            parameter("filter", filter)
        }.body()
    }

}
package com.m4ykey.network.service

import com.m4ykey.network.core.Constants.DEFAULT_FILTER
import com.m4ykey.network.core.Constants.DETAIL_FILTER
import com.m4ykey.network.core.Constants.SITE
import com.m4ykey.network.data.Items
import com.m4ykey.network.data.dto.QuestionDetailDto
import com.m4ykey.network.data.dto.QuestionDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

class QuestionService(private val client : HttpClient) {

    suspend fun getQuestions(
        site : String = SITE,
        page : Int,
        pageSize : Int = 20,
        filter : String = DEFAULT_FILTER,
        sort : String,
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
        site : String = SITE,
        filter: String = DETAIL_FILTER,
        questionId : Int
    ) : Items<QuestionDetailDto> {
        return client.get {
            url("questions/$questionId")
            parameter("site", site)
            parameter("filter", filter)
        }.body()
    }

    suspend fun getQuestionTag(
        site : String = SITE,
        page : Int,
        pageSize : Int = 20,
        filter : String = DEFAULT_FILTER,
        tag : String,
        order : String = "desc",
        sort : String
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

}
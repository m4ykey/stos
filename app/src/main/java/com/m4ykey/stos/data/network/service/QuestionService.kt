package com.m4ykey.stos.data.network.service

import com.m4ykey.stos.data.network.models.base.Items
import com.m4ykey.stos.data.network.models.question.QuestionDto
import com.m4ykey.stos.utils.Constants.QUESTION_FILTER
import com.m4ykey.stos.utils.Constants.SITE
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

class QuestionService(private val client : HttpClient) {

    suspend fun getQuestions(
        site : String = SITE,
        page : Int = 1,
        pageSize : Int = 10,
        filter : String = QUESTION_FILTER,
        sort : String,
        order : String
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

}
package com.m4ykey.network.service

import com.m4ykey.network.data.Items
import com.m4ykey.network.data.dto.QuestionDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

class QuestionService(private val client : HttpClient) {

    suspend fun getQuestions(
        site : String = "stackoverflow",
        page : Int,
        pageSize : Int,
        filter : String = "!-R-q)p7F7-ux36Owxn_EekGSsD1yy7OT9P60"
    ) : Items<QuestionDto> {
        return client.get {
            url("questions")
            parameter("site", site)
            parameter("page", page)
            parameter("pagesize", pageSize)
            parameter("filter", filter)
        }.body()
    }
}
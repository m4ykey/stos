package com.m4ykey.network.data.service

import com.m4ykey.network.core.safeApiCall
import com.m4ykey.network.data.Items
import com.m4ykey.network.data.dto.QuestionDto
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

class QuestionService {

    suspend fun getQuestions(
        site : String = "stackoverflow",
        page : Int = 1,
        pageSize : Int = 20,
        filter : String = "!-R-q)p7F7-ux36Owxn_EekGSsD1yy7OT9P60"
    ) : Result<Items<QuestionDto>> {
        return safeApiCall {
            client.get {
                url("questions")
                parameter("site", site)
                parameter("page", page)
                parameter("pagesize", pageSize)
                parameter("filter", filter)
            }.body()
        }
    }
}
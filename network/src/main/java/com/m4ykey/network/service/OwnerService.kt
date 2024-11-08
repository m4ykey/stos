package com.m4ykey.network.service

import com.m4ykey.network.core.Constants.ANSWER_FILTER
import com.m4ykey.network.core.Constants.DEFAULT_FILTER
import com.m4ykey.network.core.Constants.OWNER_FILTER
import com.m4ykey.network.core.Constants.PAGE
import com.m4ykey.network.core.Constants.PAGE_SIZE
import com.m4ykey.network.core.Constants.SITE
import com.m4ykey.network.core.withRetry
import com.m4ykey.network.data.Items
import com.m4ykey.network.data.dto.AnswerDto
import com.m4ykey.network.data.dto.OwnerDto
import com.m4ykey.network.data.dto.QuestionDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

class OwnerService(private val client : HttpClient) {

    suspend fun getOwnerById(
        site : String = SITE,
        filter : String = OWNER_FILTER,
        ownerId : Int
    ) : Items<OwnerDto> {
        return withRetry {
            client.get {
                url("users/$ownerId")
                parameter("site", site)
                parameter("filter", filter)
            }.body()
        }
    }

    suspend fun getOwnerQuestions(
        site : String = SITE,
        ownerId : Int,
        filter : String = DEFAULT_FILTER,
        page : Int = PAGE,
        pageSize : Int = PAGE_SIZE,
        sort : String = "creation"
    ) : Items<QuestionDto> {
        return withRetry {
            client.get {
                url("users/$ownerId/questions")
                parameter("site", site)
                parameter("filter", filter)
                parameter("page", page)
                parameter("pagesize", pageSize)
                parameter("sort", sort)
            }.body()
        }
    }

    suspend fun getOwnerAnswers(
        filter : String = ANSWER_FILTER,
        site : String = SITE,
        ownerId: Int,
        page : Int = PAGE,
        pageSize : Int = PAGE_SIZE,
        sort : String = "creation"
    ) : Items<AnswerDto> {
        return withRetry {
            client.get {
                url("users/$ownerId/answers")
                parameter("site", site)
                parameter("filter", filter)
                parameter("sort", sort)
                parameter("page", page)
                parameter("pagesize", pageSize)
            }.body()
        }
    }

}
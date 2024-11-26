package com.m4ykey.network.service.owner

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

class KtorOwnerService(
    private val client : HttpClient
) : OwnerService {

    override suspend fun getOwnerById(
        site : String,
        filter : String,
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

    override suspend fun getOwnerQuestions(
        site : String,
        ownerId : Int,
        filter : String,
        page : Int,
        pageSize : Int,
        sort : String
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

    override suspend fun getOwnerAnswers(
        filter : String,
        site : String,
        ownerId: Int,
        page : Int,
        pageSize : Int,
        sort : String
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
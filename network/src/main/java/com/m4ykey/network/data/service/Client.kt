package com.m4ykey.network.data.service

import com.m4ykey.network.BuildConfig.STACK_API_KEY
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.parameters
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

val client = HttpClient(OkHttp) {
    defaultRequest {
        url {
            host = "api.stackexchange.com"
            path("2.3")
            parameters.append("key", STACK_API_KEY)
        }
    }

    install(Logging) {
        logger = Logger.SIMPLE
        level = LogLevel.BODY
    }

    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            }
        )
    }
}
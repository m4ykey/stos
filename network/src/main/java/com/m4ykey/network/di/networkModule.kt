package com.m4ykey.network.di

import com.m4ykey.network.BuildConfig.STACK_API_KEY
import com.m4ykey.network.service.OwnerService
import com.m4ykey.network.service.QuestionService
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single { HttpClient(OkHttp) {
        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
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
    } }

    single { QuestionService(get()) }
    single { OwnerService(get()) }
}
package com.m4ykey.stos.data.network.di

import com.m4ykey.stos.data.network.QuestionService
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.http.path
import kotlinx.serialization.json.Json
import io.ktor.serialization.kotlinx.json.json
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClient(CIO) {
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api.stackexchange.com"
                    path("2.3")
                }
            }

            install(Logging) {
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

            install(HttpTimeout) {
                requestTimeoutMillis = 10000
                connectTimeoutMillis = 10000
                socketTimeoutMillis = 10000
            }
        }
    }

    single {
        QuestionService(get())
    }
}
package com.m4ykey.stos.core.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object NetworkClient {

    fun create(engine : HttpClientEngine) : HttpClient {
        return HttpClient(engine) {
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api.stackexchange.com"
                    path("2.3")
                    parameters.append("key", "")
                }
            }

            install(Logging) {
                level = LogLevel.BODY
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }

}
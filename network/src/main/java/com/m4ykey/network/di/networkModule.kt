package com.m4ykey.network.di

import android.app.Application
import android.util.Log
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.m4ykey.network.BuildConfig.STACK_API_KEY
import com.m4ykey.network.service.owner.KtorOwnerService
import com.m4ykey.network.service.question.KtorQuestionService
import com.m4ykey.network.service.search.KtorSearchService
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single {
        val context : Application = get()

        val chuckerInterceptor = ChuckerInterceptor.Builder(context)
            .collector(ChuckerCollector(context))
            .maxContentLength(250000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()

        HttpClient(OkHttp) {
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api.stackexchange.com"
                    path("2.3")
                    parameters.append("key", STACK_API_KEY)
                }
            }

            install(UserAgent) {
                agent = "MyApp/1.0"
            }

            install(Logging) {
                logger = object : Logger {
                    private val logTag = "NetworkLogger"
                    override fun log(message: String) {
                        Log.d(logTag, message)
                    }
                }
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
                requestTimeoutMillis = 10_000
                connectTimeoutMillis = 10_000
                socketTimeoutMillis = 10_000
            }

            engine {
                addInterceptor(chuckerInterceptor)
            }
        }
    }

    single { KtorQuestionService(get()) }
    single { KtorOwnerService(get()) }
    single { KtorSearchService(get()) }
}
package com.m4ykey.stos.di

import android.app.Application
import android.util.Log
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.m4ykey.stos.BuildConfig
import com.m4ykey.stos.data.network.service.QuestionService
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
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
                    parameters.append("key", BuildConfig.STACK_API_KEY)
                }
            }

            install(Logging) {
                logger = object : Logger {
                    private val logTag = "NetworkLogger"
                    override fun log(message: String) {
                        Log.i(logTag, message)
                    }
                }
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

            engine {
                addInterceptor(chuckerInterceptor)
            }
        }
    }

    single {
        QuestionService(get())
    }
}
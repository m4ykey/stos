package com.m4ykey.stos.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import okhttp3.ConnectionPool
import org.koin.core.module.Module
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> {
            OkHttp.create {
                config {
                    connectionPool(ConnectionPool(5, 2, TimeUnit.MINUTES))
                    connectTimeout(5, TimeUnit.SECONDS)
                    readTimeout(10, TimeUnit.SECONDS)
                    retryOnConnectionFailure(true)
                }
            }
        }
    }
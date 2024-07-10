package com.m4ykey.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class KeyInterceptor(private val apiKey : String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestWithKey = originalRequest.newBuilder()
            .header("key", apiKey)
            .build()

        return chain.proceed(requestWithKey)
    }
}
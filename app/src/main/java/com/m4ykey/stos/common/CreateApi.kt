package com.m4ykey.stos.common

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

fun <T> createApi(baseUrl : String, moshi : Moshi, apiClass : Class<T>, client : OkHttpClient) : T {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(apiClass)
}
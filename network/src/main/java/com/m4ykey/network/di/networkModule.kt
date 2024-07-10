package com.m4ykey.network.di

import com.m4ykey.network.BuildConfig
import com.m4ykey.network.interceptor.KeyInterceptor
import com.m4ykey.network.service.QuestionService
import com.m4ykey.network.util.Urls.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    single { KeyInterceptor(BuildConfig.STACK_API_KEY) }

    single { OkHttpClient.Builder()
        .addInterceptor(get<KeyInterceptor>())
        .build() }

    single { get<Retrofit>().create(QuestionService::class.java) }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    single<Moshi> {
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

}
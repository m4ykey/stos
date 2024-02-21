package com.m4ykey.stos.di

import com.m4ykey.stos.common.Constants.BASE_URL
import com.m4ykey.stos.common.createApi
import com.m4ykey.stos.data.api.QuestionApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideQuestionApi(moshi: Moshi) : QuestionApi = createApi(BASE_URL, moshi, QuestionApi::class.java)

    @Provides
    @Singleton
    fun provideMoshi() : Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

}
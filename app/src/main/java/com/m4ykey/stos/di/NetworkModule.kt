package com.m4ykey.stos.di

import android.util.Log
import com.m4ykey.stos.common.Constants.BASE_URL
import com.m4ykey.stos.common.createApi
import com.m4ykey.stos.data.api.QuestionApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton
import kotlin.math.log

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideQuestionApi(
        moshi: Moshi,
        client: OkHttpClient
    ): QuestionApi = createApi(BASE_URL, moshi, QuestionApi::class.java, client)

    @Provides
    @Singleton
    fun provideMoshi() : Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor { message ->
            Log.d("OkHttp", message)
        }
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

}
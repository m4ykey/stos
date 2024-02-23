package com.m4ykey.stos.data.api

import com.m4ykey.stos.BuildConfig
import com.m4ykey.stos.data.model.question.QuestionListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface QuestionApi {

    @GET("questions")
    suspend fun getQuestions(
        @Query("order") order : String = "desc",
        @Query("sort") sort : String = "hot",
        @Query("site") site : String = "stackoverflow",
        @Query("key") key : String = BuildConfig.STACK_API_KEY,
        @Query("page") page : Int,
        @Query("pagesize") pageSize : Int,
    ) : QuestionListDto

}
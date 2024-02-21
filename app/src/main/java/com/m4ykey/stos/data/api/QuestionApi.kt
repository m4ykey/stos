package com.m4ykey.stos.data.api

import com.m4ykey.stos.data.model.question.QuestionListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface QuestionApi {

    @GET("questions")
    suspend fun getQuestions(
        @Query("page") page : Int,
        @Query("pagesize") pageSize : Int,
        @Query("order") order : String = "desc",
        @Query("sort") sort : String,
        @Query("site") site : String = "stackoverflow"
    ) : QuestionListDto

}
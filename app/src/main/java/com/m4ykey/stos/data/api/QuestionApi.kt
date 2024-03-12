package com.m4ykey.stos.data.api

import com.m4ykey.stos.data.model.question.QuestionDto
import com.m4ykey.stos.common.Constants.API_KEY
import com.m4ykey.stos.common.Constants.SITE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface QuestionApi {

    @GET("questions")
    suspend fun getQuestions(
        @Query("order") order : String = "desc",
        @Query("sort") sort : String,
        @Query("site") site : String = SITE,
        @Query("key") key : String = API_KEY,
        @Query("page") page : Int,
        @Query("pagesize") pageSize : Int,
    ) : QuestionDto

    @GET("questions/{id}")
    suspend fun getQuestionDetail(
        @Path("id") questionId : Int,
        @Query("filter") filter : String = "withbody",
        @Query("key") key : String = API_KEY,
        @Query("site") site : String = SITE
    ) : QuestionDto

}
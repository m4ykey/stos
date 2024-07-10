package com.m4ykey.network.service

import com.m4ykey.network.model.Items
import com.m4ykey.network.model.Question
import com.m4ykey.network.util.Filters.QUESTION_LIST_FILTER
import com.m4ykey.network.util.Urls.SITE
import retrofit2.http.GET
import retrofit2.http.Query

interface QuestionService {

    @GET("questions")
    suspend fun getQuestions(
        @Query("site") site : String = SITE,
        @Query("page") page : Int = 1,
        @Query("pagesize") pageSize : Int = 20,
        @Query("filter") filter : String = QUESTION_LIST_FILTER
    ) : Items<Question>

}
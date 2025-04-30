package com.m4ykey.stos.question.data.network

import com.m4ykey.stos.core.Filters.QUESTION_DETAIL_FILTER
import com.m4ykey.stos.core.Filters.QUESTION_FILTER
import com.m4ykey.stos.question.data.network.model.Items
import com.m4ykey.stos.question.data.network.model.QuestionDetailDto
import com.m4ykey.stos.question.data.network.model.QuestionDto

const val baseSite : String = "stackoverflow"

interface RemoteQuestionService {

    suspend fun getQuestions(
        site : String = baseSite,
        page : Int,
        pageSize : Int,
        filter : String = QUESTION_FILTER,
        sort : String = "hot"
    ) : Items<QuestionDto>

    suspend fun getQuestionById(
        site : String = baseSite,
        filter : String = QUESTION_DETAIL_FILTER,
        id : Int
    ) : Items<QuestionDetailDto>

}
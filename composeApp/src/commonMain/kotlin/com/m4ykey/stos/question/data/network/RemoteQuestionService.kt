package com.m4ykey.stos.question.data.network

import com.m4ykey.stos.question.data.network.Filters.QUESTION_FILTER
import com.m4ykey.stos.question.data.network.model.Items
import com.m4ykey.stos.question.data.network.model.QuestionDto

interface RemoteQuestionService {

    suspend fun getQuestions(
        site : String = "stackoverflow",
        page : Int,
        pageSize : Int,
        filter : String = QUESTION_FILTER,
        sort : String = "hot"
    ) : Items<QuestionDto>

}
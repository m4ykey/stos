package com.m4ykey.stos.question.data.network

import com.m4ykey.stos.core.Filters.QUESTION_ANSWER_FILTER
import com.m4ykey.stos.core.Filters.QUESTION_DETAIL_FILTER
import com.m4ykey.stos.core.Filters.QUESTION_FILTER
import com.m4ykey.stos.core.model.Items
import com.m4ykey.stos.question.data.network.model.QuestionAnswerDto
import com.m4ykey.stos.question.data.network.model.QuestionDetailDto
import com.m4ykey.stos.question.data.network.model.QuestionDto

interface RemoteQuestionService {

    suspend fun getQuestionAnswers(
        filter : String = QUESTION_ANSWER_FILTER,
        id : Int
    ) : Items<QuestionAnswerDto>

    suspend fun getQuestions(
        page : Int,
        pageSize : Int,
        filter : String = QUESTION_FILTER,
        sort : String = "hot",
        order : String = "desc"
    ) : Items<QuestionDto>

    suspend fun getQuestionById(
        filter : String = QUESTION_DETAIL_FILTER,
        id : Int
    ) : Items<QuestionDetailDto>

    suspend fun getQuestionByTag(
        page : Int,
        pageSize : Int,
        filter : String = QUESTION_FILTER,
        tagged : String,
        order : String = "desc",
        sort : String = "hot"
    ) : Items<QuestionDto>

}
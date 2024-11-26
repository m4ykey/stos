package com.m4ykey.network.service.question

import com.m4ykey.network.core.Constants.ANSWER_FILTER
import com.m4ykey.network.core.Constants.DEFAULT_FILTER
import com.m4ykey.network.core.Constants.DETAIL_FILTER
import com.m4ykey.network.core.Constants.PAGE
import com.m4ykey.network.core.Constants.PAGE_SIZE
import com.m4ykey.network.core.Constants.SITE
import com.m4ykey.network.data.Items
import com.m4ykey.network.data.dto.AnswerDto
import com.m4ykey.network.data.dto.QuestionDetailDto
import com.m4ykey.network.data.dto.QuestionDto

interface QuestionService {

    suspend fun getQuestions(
        filter : String = DEFAULT_FILTER,
        sort : String,
        page : Int = PAGE,
        pageSize : Int = PAGE_SIZE,
        site : String = SITE,
        order : String = "desc"
    ) : Items<QuestionDto>

    suspend fun getQuestionDetail(
        filter: String = DETAIL_FILTER,
        questionId : Int,
        site : String = SITE
    ) : Items<QuestionDetailDto>

    suspend fun getQuestionTag(
        filter: String = DEFAULT_FILTER,
        tag: String,
        sort: String,
        page: Int = PAGE,
        site : String = SITE,
        order : String = "desc",
        pageSize : Int = PAGE_SIZE
    ) : Items<QuestionDto>

    suspend fun getQuestionAnswer(
        questionId : Int,
        site : String = SITE,
        page : Int = PAGE,
        pageSize: Int = PAGE_SIZE,
        filter: String = ANSWER_FILTER
    ) : Items<AnswerDto>

}
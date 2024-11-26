package com.m4ykey.network.service.owner

import com.m4ykey.network.core.Constants.ANSWER_FILTER
import com.m4ykey.network.core.Constants.DEFAULT_FILTER
import com.m4ykey.network.core.Constants.OWNER_FILTER
import com.m4ykey.network.core.Constants.PAGE
import com.m4ykey.network.core.Constants.PAGE_SIZE
import com.m4ykey.network.core.Constants.SITE
import com.m4ykey.network.data.Items
import com.m4ykey.network.data.dto.AnswerDto
import com.m4ykey.network.data.dto.OwnerDto
import com.m4ykey.network.data.dto.QuestionDto

interface OwnerService {

    suspend fun getOwnerById(
        site : String = SITE,
        filter : String = OWNER_FILTER,
        ownerId : Int
    ) : Items<OwnerDto>

    suspend fun getOwnerQuestions(
        site : String = SITE,
        ownerId : Int,
        filter : String = DEFAULT_FILTER,
        page : Int = PAGE,
        pageSize : Int = PAGE_SIZE,
        sort : String = "creation"
    ) : Items<QuestionDto>

    suspend fun getOwnerAnswers(
        filter : String = ANSWER_FILTER,
        site : String = SITE,
        ownerId: Int,
        page : Int = PAGE,
        pageSize : Int = PAGE_SIZE,
        sort : String = "creation"
    ) : Items<AnswerDto>

}
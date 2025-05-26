package com.m4ykey.stos.search.data.network

import com.m4ykey.stos.core.Filters.QUESTION_FILTER
import com.m4ykey.stos.question.data.network.model.Items
import com.m4ykey.stos.question.data.network.model.QuestionDto

interface RemoteSearchService {

    suspend fun search(
        page : Int,
        pageSize : Int,
        filter : String = QUESTION_FILTER,
        inTitle : String?,
        sort : String = "activity",
        tagged : String,
        order : String
    ) : Items<QuestionDto>

}
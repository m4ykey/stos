package com.m4ykey.network.service.search

import com.m4ykey.network.core.Constants.DEFAULT_FILTER
import com.m4ykey.network.core.Constants.PAGE
import com.m4ykey.network.core.Constants.PAGE_SIZE
import com.m4ykey.network.core.Constants.SITE
import com.m4ykey.network.data.Items
import com.m4ykey.network.data.dto.QuestionDto

interface SearchService {

    suspend fun searchQuestions(
        page : Int = PAGE,
        pageSize : Int = PAGE_SIZE,
        filter : String = DEFAULT_FILTER,
        inTitle : String? = null,
        site : String = SITE,
        sort : String = "activity",
        tagged : String? = null
    ) : Items<QuestionDto>

}
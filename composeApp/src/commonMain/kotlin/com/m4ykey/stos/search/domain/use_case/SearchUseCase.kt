package com.m4ykey.stos.search.domain.use_case

import app.cash.paging.PagingData
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class SearchUseCase(
    private val repository: SearchRepository
) {

    fun search(
        inTitle : String,
        order : String,
        page : Int,
        pageSize : Int,
        sort : String,
        tagged : String
    ) : Flow<PagingData<Question>> {
        return repository.search(
            order = order,
            page = page,
            pageSize = pageSize,
            sort = sort,
            inTitle = inTitle,
            tagged = tagged
        )
    }

}
package com.m4ykey.stos.search.domain.repository

import app.cash.paging.PagingData
import com.m4ykey.stos.question.domain.model.Question
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun search(
        page : Int,
        pageSize : Int,
        sort : String,
        inTitle : String,
        tagged : String,
        order : String
    ) : Flow<PagingData<Question>>

}
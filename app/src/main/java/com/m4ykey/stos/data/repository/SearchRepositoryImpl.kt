package com.m4ykey.stos.data.repository

import androidx.paging.PagingData
import com.m4ykey.network.core.createPager
import com.m4ykey.network.data.model.Question
import com.m4ykey.network.data.repository.SearchRepository
import com.m4ykey.network.paging.search.SearchPagingSource
import com.m4ykey.network.service.SearchService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class SearchRepositoryImpl(private val service : SearchService) : SearchRepository {

    override suspend fun searchQuestions(inTitle: String): Flow<PagingData<Question>> = createPager {
        SearchPagingSource(service = service, inTitle = inTitle)
    }.flowOn(Dispatchers.IO)

}
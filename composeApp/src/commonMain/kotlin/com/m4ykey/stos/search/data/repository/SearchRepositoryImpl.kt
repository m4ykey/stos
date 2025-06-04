package com.m4ykey.stos.search.data.repository

import app.cash.paging.Pager
import app.cash.paging.PagingData
import com.m4ykey.stos.core.paging.pagingConfig
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.search.data.network.RemoteSearchService
import com.m4ykey.stos.search.data.paging.SearchPaging
import com.m4ykey.stos.search.domain.repository.SearchRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class SearchRepositoryImpl(
    private val remoteSearchService: RemoteSearchService,
    private val dispatcherIO : CoroutineDispatcher
) : SearchRepository {

    override suspend fun search(
        page: Int,
        pageSize: Int,
        sort: String,
        inTitle: String,
        tagged: String,
        order : String
    ): Flow<PagingData<Question>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                SearchPaging(service = remoteSearchService, inTitle, tagged)
            }
        ).flow.flowOn(dispatcherIO)
    }
}
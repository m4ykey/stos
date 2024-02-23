package com.m4ykey.stos.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.m4ykey.stos.common.Constants.PAGE_SIZE
import com.m4ykey.stos.data.api.QuestionApi
import com.m4ykey.stos.data.domain.model.question.QuestionItem
import com.m4ykey.stos.data.domain.repository.QuestionRepository
import com.m4ykey.stos.data.paging.QuestionsPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor(
    private val api : QuestionApi
) : QuestionRepository {

    override suspend fun getQuestions(): Flow<PagingData<QuestionItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                QuestionsPagingSource(api = api)
            }
        ).flow
    }
}
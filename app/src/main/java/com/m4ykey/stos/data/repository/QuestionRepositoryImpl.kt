package com.m4ykey.stos.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.m4ykey.stos.common.Constants.PAGE_SIZE
import com.m4ykey.stos.common.Resource
import com.m4ykey.stos.common.safeApiCall
import com.m4ykey.stos.data.api.QuestionApi
import com.m4ykey.stos.data.domain.model.question.QuestionItem
import com.m4ykey.stos.data.domain.repository.QuestionRepository
import com.m4ykey.stos.data.model.mapper.toQuestionItem
import com.m4ykey.stos.data.paging.QuestionsPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor(
    private val api : QuestionApi
) : QuestionRepository {

    private val pagingConfig = PagingConfig(
        pageSize = PAGE_SIZE,
        enablePlaceholders = false
    )

    override suspend fun getQuestions(sort : String): Flow<PagingData<QuestionItem>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                QuestionsPagingSource(
                    api = api,
                    sort = sort
                )
            }
        ).flow
    }

    override suspend fun getQuestionDetails(id: Int): Flow<Resource<QuestionItem>> = flow {
        emit(Resource.Loading())
        emit(safeApiCall {
            api.getQuestionDetail(questionId = id).items.map { it.toQuestionItem() }.first()
        })
    }
}
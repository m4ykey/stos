package com.m4ykey.stos.data.repository

import androidx.paging.PagingData
import com.m4ykey.network.core.createPager
import com.m4ykey.network.core.safeApiCall
import com.m4ykey.network.data.model.Answer
import com.m4ykey.network.data.model.Owner
import com.m4ykey.network.data.model.Question
import com.m4ykey.network.data.repository.OwnerRepository
import com.m4ykey.network.data.toOwner
import com.m4ykey.network.paging.owner.OwnerAnswerPagingSource
import com.m4ykey.network.paging.owner.OwnerQuestionPagingSource
import com.m4ykey.network.service.owner.KtorOwnerService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class OwnerRepositoryImpl(private val service : KtorOwnerService) : OwnerRepository {

    override suspend fun getOwnerById(ownerId: Int): Flow<Owner> = flow {
        val result = safeApiCall {
            service.getOwnerById(ownerId = ownerId).items.map { it.toOwner() }.first()
        }
        if (result.isSuccess) {
            emit(result.getOrThrow())
        } else {
            throw result.exceptionOrNull() ?: Exception("Unknown error")
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getOwnerQuestions(ownerId: Int): Flow<PagingData<Question>> = createPager {
        OwnerQuestionPagingSource(ownerId = ownerId, service = service)
    }

    override suspend fun getOwnerAnswers(ownerId: Int): Flow<PagingData<Answer>> = createPager {
        OwnerAnswerPagingSource(service = service, ownerId = ownerId)
    }
}
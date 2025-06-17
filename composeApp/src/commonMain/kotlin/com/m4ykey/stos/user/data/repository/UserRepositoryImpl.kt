package com.m4ykey.stos.user.data.repository

import com.m4ykey.stos.core.model.Items
import com.m4ykey.stos.core.network.ApiResult
import com.m4ykey.stos.core.network.safeApi
import com.m4ykey.stos.user.data.mapper.toUser
import com.m4ykey.stos.user.data.network.RemoteUserService
import com.m4ykey.stos.user.data.network.model.UserDto
import com.m4ykey.stos.user.domain.model.User
import com.m4ykey.stos.user.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepositoryImpl(
    private val remoteUserService : RemoteUserService,
    private val dispatcherIO : CoroutineDispatcher
) : UserRepository {

    override fun getUser(id: Int): Flow<ApiResult<User>> = flow {
        val result = safeApi<Items<UserDto>> {
            remoteUserService.getUser(id = id)
        }

        when (result) {
            is ApiResult.Success -> {
                val user = result.data.items?.map { it.toUser() }?.firstOrNull()
                if (user != null) emit(ApiResult.Success(user))
            }
            is ApiResult.Failure -> emit(ApiResult.Failure(result.exception))
        }
    }.flowOn(dispatcherIO)
}
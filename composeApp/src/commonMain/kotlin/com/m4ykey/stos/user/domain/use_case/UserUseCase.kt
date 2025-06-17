package com.m4ykey.stos.user.domain.use_case

import com.m4ykey.stos.core.network.ApiResult
import com.m4ykey.stos.user.domain.model.User
import com.m4ykey.stos.user.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserUseCase(
    private val repository: UserRepository
) {

    fun getUser(id : Int) : Flow<ApiResult<User>> {
        return repository.getUser(id)
    }

}
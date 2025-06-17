package com.m4ykey.stos.user.domain.repository

import com.m4ykey.stos.core.network.ApiResult
import com.m4ykey.stos.user.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUser(id : Int) : Flow<ApiResult<User>>

}
package com.m4ykey.stos.user.data.network

import com.m4ykey.stos.core.Filters.OWNER_FILTER
import com.m4ykey.stos.core.model.Items
import com.m4ykey.stos.user.data.network.model.UserDto

interface RemoteUserService {

    suspend fun getUser(
        filter : String = OWNER_FILTER,
        id : Int
    ) : Items<UserDto>

}
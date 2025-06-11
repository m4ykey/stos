package com.m4ykey.stos.owner.data.network

import com.m4ykey.stos.core.Filters.OWNER_FILTER
import com.m4ykey.stos.core.model.Items
import com.m4ykey.stos.owner.data.network.model.UserDto

interface RemoteOwnerService {

    suspend fun getUser(
        filter : String = OWNER_FILTER,
        id : Int
    ) : Items<UserDto>

}
package com.m4ykey.stos.owner.data.network

import com.m4ykey.stos.core.model.Items
import com.m4ykey.stos.owner.data.network.model.UserDto
import io.ktor.client.HttpClient

class OwnerService(
    private val client : HttpClient
) : RemoteOwnerService {

    override suspend fun getUser(
        filter: String,
        id: Int
    ): Items<UserDto> {
        TODO("Not yet implemented")
    }
}
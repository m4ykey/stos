package com.m4ykey.stos.user.data.network

import com.m4ykey.stos.core.model.Items
import com.m4ykey.stos.core.network.setParameters
import com.m4ykey.stos.user.data.network.model.UserDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments

class UserService(
    private val client : HttpClient
) : RemoteUserService {

    override suspend fun getUser(
        filter: String,
        id: Int
    ): Items<UserDto> {
        return client.get {
            url {
                appendPathSegments("users/$id")
                setParameters(
                    "filter" to filter
                )
            }
        }.body()
    }
}
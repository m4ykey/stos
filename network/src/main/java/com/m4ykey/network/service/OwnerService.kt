package com.m4ykey.network.service

import com.m4ykey.network.core.Constants.OWNER_FILTER
import com.m4ykey.network.core.Constants.SITE
import com.m4ykey.network.data.Items
import com.m4ykey.network.data.dto.OwnerDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

class OwnerService(private val client : HttpClient) {

    suspend fun getOwnerById(
        site : String = SITE,
        filter : String = OWNER_FILTER,
        ownerId : Int
    ) : Items<OwnerDto> {
        return client.get {
            url("users/$ownerId")
            parameter("site", site)
            parameter("filter", filter)
        }.body()
    }

}
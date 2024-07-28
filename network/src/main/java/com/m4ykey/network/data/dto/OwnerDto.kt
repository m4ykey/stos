package com.m4ykey.network.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class OwnerDto(
    val display_name: String?,
    val link: String?,
    val profile_image: String?,
    val reputation: Int?,
    val user_id: Int?
)
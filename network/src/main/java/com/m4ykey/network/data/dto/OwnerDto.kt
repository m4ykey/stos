package com.m4ykey.network.data.dto

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class OwnerDto(
    val display_name: String?,
    val link: String?,
    val profile_image: String?,
    val reputation: Int?,
    val user_id: Int?
)
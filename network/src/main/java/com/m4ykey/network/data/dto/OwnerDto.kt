package com.m4ykey.network.data.dto

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class OwnerDto(
    val display_name: String? = null,
    val link: String? = null,
    val profile_image: String? = null,
    val reputation: Int? = -1,
    val user_id: Int? = -1,
    val badge_counts : BadgeCountsDto? = null,
    val location : String? = null
)
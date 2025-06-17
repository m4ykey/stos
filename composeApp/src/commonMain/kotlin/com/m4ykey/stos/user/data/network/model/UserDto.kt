package com.m4ykey.stos.user.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val reputation : Int? = 0,
    @SerialName("user_id") val userId : Int? = 0,
    val link : String? = null,
    @SerialName("profile_image") val profileImage : String? = null,
    @SerialName("display_name") val displayName : String? = null,
    @SerialName("badge_counts") val badgeCounts: BadgeCountsDto? = null
)
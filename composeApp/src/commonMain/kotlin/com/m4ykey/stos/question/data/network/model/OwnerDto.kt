package com.m4ykey.stos.question.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OwnerDto(
    val reputation : Int? = 0,
    @SerialName("user_id") val userId : Int? = 0,
    @SerialName("profile_image") val profileImage : String? = null,
    @SerialName("display_name") val displayName : String? = null,
    val link : String? = null,
    @SerialName("badge_counts") val badgeCounts : BadgeCountsDto? = null
)

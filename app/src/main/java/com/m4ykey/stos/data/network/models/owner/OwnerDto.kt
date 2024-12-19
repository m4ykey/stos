package com.m4ykey.stos.data.network.models.owner

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class OwnerDto(
    val reputation : Int? = null,
    @SerialName("user_id") val userId : Int? = null,
    @SerialName("profile_image") val profileImage : String? = null,
    @SerialName("display_name") val displayName : String? = null,
    val link : String? = null,
    val badgeCounts : BadgeCountsDto? = null
)

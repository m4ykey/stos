package com.m4ykey.stos.data.network.models.owner

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class OwnerDto(
    val reputation : Int,
    val user_id : Int,
    val profile_image : String,
    val display_name : String,
    val link : String,
    val badgeCounts : BadgeCountsDto
)

package com.m4ykey.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LastEditor(
    @Json(name = "badge_counts") val badgeCounts: BadgeCounts? = null,
    @Json(name = "display_name") val displayName: String? = null,
    val link: String? = null,
    @Json(name = "profile_image") val profileImage: String? = null,
    val reputation: Int? = null,
    @Json(name = "user_id") val userId: Int? = null
)
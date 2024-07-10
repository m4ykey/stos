package com.m4ykey.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ClosedDetails(
    val description: String? = null,
    val reason: String? = null
)
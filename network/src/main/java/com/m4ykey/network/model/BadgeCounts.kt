package com.m4ykey.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BadgeCounts(
    val bronze: Int? = null,
    val gold: Int? = null,
    val silver: Int? = null
)
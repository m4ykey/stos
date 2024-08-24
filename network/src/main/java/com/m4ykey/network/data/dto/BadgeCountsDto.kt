package com.m4ykey.network.data.dto

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class BadgeCountsDto(
    val bronze : Int,
    val silver : Int,
    val gold : Int
)

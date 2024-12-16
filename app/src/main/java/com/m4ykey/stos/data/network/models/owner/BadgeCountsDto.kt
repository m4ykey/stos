package com.m4ykey.stos.data.network.models.owner

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class BadgeCountsDto(
    val bronze : Int,
    val silver : Int,
    val gold : Int
)

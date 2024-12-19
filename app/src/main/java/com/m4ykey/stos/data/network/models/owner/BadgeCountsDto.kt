package com.m4ykey.stos.data.network.models.owner

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class BadgeCountsDto(
    val bronze : Int? = null,
    val silver : Int? = null,
    val gold : Int? = null
)

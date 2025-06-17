package com.m4ykey.stos.user.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class BadgeCountsDto(
    val bronze : Int? = 0,
    val silver : Int? = 0,
    val gold : Int? = 0
)
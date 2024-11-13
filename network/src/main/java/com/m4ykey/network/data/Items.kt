package com.m4ykey.network.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Items<T>(
    @SerialName("items")
    val items : List<T> = emptyList(),
    @SerialName("backoff")
    val backoff : Int? = 0
)
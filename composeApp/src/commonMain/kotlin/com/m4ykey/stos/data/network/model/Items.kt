package com.m4ykey.stos.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Items<T>(
    val items : List<T>? = emptyList()
)

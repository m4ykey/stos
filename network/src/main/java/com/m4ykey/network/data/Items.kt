package com.m4ykey.network.data

import kotlinx.serialization.Serializable

@Serializable
data class Items<T>(
    val items : List<T>
)
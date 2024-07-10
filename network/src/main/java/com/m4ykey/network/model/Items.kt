package com.m4ykey.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Items<T>(
    val items : List<T>
)

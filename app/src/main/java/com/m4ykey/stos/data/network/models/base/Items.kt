package com.m4ykey.stos.data.network.models.base

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class Items<T>(
    val items : List<T>
)
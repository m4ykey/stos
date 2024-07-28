package com.m4ykey.network.data.model

import androidx.annotation.Keep

@Keep
data class Owner(
    val displayName : String,
    val link : String,
    val profileImage : String,
    val reputation : Int,
    val userId : Int
)

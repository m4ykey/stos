package com.m4ykey.stos.data.domain.model

data class Owner(
    val reputation : Int,
    val userId : Int,
    val profileImage : String,
    val displayName : String,
    val link : String,
    val badgeCounts : BadgeCounts
)

package com.m4ykey.stos.user.domain.model

data class User(
    val reputation : Int,
    val userId : Int,
    val profileImage : String,
    val displayName : String,
    val badgeCounts: BadgeCounts
)

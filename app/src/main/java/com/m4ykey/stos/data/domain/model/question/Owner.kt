package com.m4ykey.stos.data.domain.model.question

data class Owner(
    val acceptRate : Int,
    val accountId : Int,
    val displayName : String,
    val link : String,
    val profileImage : String,
    val reputation : Int,
    val userId : Int
)

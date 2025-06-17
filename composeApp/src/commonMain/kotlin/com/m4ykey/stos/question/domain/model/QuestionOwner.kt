package com.m4ykey.stos.question.domain.model

import com.m4ykey.stos.user.domain.model.BadgeCounts

data class QuestionOwner(
    val reputation : Int,
    val userId : Int,
    val profileImage : String,
    val displayName : String,
    val link : String,
    val badgeCounts : BadgeCounts
)

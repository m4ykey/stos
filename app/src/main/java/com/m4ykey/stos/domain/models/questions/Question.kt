package com.m4ykey.stos.domain.models.questions

import com.m4ykey.stos.domain.models.owner.Owner

data class Question(
    val viewCount : Int,
    val downVoteCount : Int,
    val upVoteCount : Int?,
    val answerCount : Int,
    val creationDate : Int,
    val questionId : Int?,
    val title : String,
    val owner : Owner
)

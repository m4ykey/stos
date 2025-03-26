package com.m4ykey.stos.data.domain.model

data class Question(
    val owner : Owner,
    val viewCount : Int,
    val downVoteCount : Int,
    val upVoteCount : Int,
    val answerCount : Int,
    val creationDate : Int,
    val questionId : Int,
    val title : String
)

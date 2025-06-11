package com.m4ykey.stos.question.domain.model

data class QuestionAnswer(
    val bodyMarkdown : String,
    val creationDate : Int,
    val downVoteCount : Int,
    val owner : QuestionOwner,
    val upVoteCount : Int,
    val answerId : Int
)

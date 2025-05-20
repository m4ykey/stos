package com.m4ykey.stos.question.domain.model

data class Answer(
    val bodyMarkdown : String,
    val creationDate : Int,
    val downVoteCount : Int,
    val owner : Owner,
    val upVoteCount : Int,
    val answerId : Int
)

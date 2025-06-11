package com.m4ykey.stos.question.domain.model

data class Question(
    val owner : QuestionOwner,
    val viewCount : Int,
    val downVoteCount : Int,
    val upVoteCount : Int,
    val answerCount : Int,
    val creationDate : Int,
    val questionId : Int,
    val title : String
)

package com.m4ykey.network.data.model

data class Question(
    val answerCount : Int,
    val creationDate : Int,
    val downVoteCount : Int,
    val owner: Owner,
    val questionId : Int,
    val title : String,
    val upVoteCount : Int,
    val viewCount : Int
)

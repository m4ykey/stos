package com.m4ykey.network.data.model

data class Answer(
    val answerId : Int,
    val bodyMarkdown : String,
    val commentCount : Int,
    val creationDate : Int,
    val downVoteCount : Int,
    val owner : Owner,
    val questionId : Int,
    val upVoteCount : Int,
    val title : String
)

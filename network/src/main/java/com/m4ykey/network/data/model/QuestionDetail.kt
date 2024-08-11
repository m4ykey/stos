package com.m4ykey.network.data.model

data class QuestionDetail(
    val answerCount : Int,
    val creationDate : Int,
    val downVoteCount : Int,
    val owner: Owner,
    val questionId : Int,
    val title : String,
    val upVoteCount : Int,
    val viewCount : Int,
    val tags : List<String>,
    val lastEditor : Owner,
    val lastActivityDate : Int,
    val lastEditDate : Int,
    val bodyMarkdown : String,
    val link : String
)

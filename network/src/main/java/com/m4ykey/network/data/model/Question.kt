package com.m4ykey.network.data.model

import androidx.annotation.Keep

@Keep
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

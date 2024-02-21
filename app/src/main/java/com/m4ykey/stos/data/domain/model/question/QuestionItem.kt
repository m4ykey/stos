package com.m4ykey.stos.data.domain.model.question

data class QuestionItem(
    val answerCount : Int,
    val owner : Owner,
    val questionId : Int,
    val score : Int,
    val tags : List<String>,
    val title : String,
    val viewCount : Int
)

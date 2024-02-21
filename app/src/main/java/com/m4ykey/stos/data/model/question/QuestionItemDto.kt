package com.m4ykey.stos.data.model.question

data class QuestionItemDto(
    val answer_count: Int,
    val owner: OwnerDto,
    val question_id: Int,
    val score: Int,
    val tags: List<String>,
    val title: String,
    val view_count: Int
)
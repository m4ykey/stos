package com.m4ykey.stos.data.model.question

data class QuestionItemDto(
    val answer_count: Int? = 0,
    val owner: OwnerDto,
    val question_id: Int? = 0,
    val score: Int? = 0,
    val tags: List<String>? = emptyList(),
    val title: String? = null,
    val view_count: Int? = 0,
    val creation_date : Int? = 0
)
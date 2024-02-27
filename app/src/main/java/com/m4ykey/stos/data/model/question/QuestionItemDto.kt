package com.m4ykey.stos.data.model.question

data class QuestionItemDto(
    val accepted_answer_id: Int? = 0,
    val answer_count: Int? = 0,
    val body: String? = null,
    val closed_date: Int? = 0,
    val closed_reason: String? = null,
    val content_license: String? = null,
    val creation_date: Int? = 0,
    val is_answered: Boolean? = false,
    val last_activity_date: Int? = 0,
    val last_edit_date: Int? = 0,
    val link: String? = null,
    val owner: OwnerDto,
    val question_id: Int? = 0,
    val score: Int? = 0,
    val tags: List<String> = emptyList(),
    val title: String? = null,
    val view_count: Int? = 0
)
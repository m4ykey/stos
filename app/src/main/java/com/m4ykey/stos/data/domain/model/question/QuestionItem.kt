package com.m4ykey.stos.data.domain.model.question

data class QuestionItem(
    val acceptedAnswerId: Int,
    val answerCount: Int,
    val body: String,
    val closedDate: Int,
    val closedReason: String,
    val contentLicense: String,
    val creationDate: Int,
    val isAnswered: Boolean,
    val lastActivityDate: Int,
    val lastEditDate: Int,
    val link: String,
    val owner: Owner,
    val questionId: Int,
    val score: Int,
    val tags: List<String>,
    val title: String,
    val viewCount: Int
)

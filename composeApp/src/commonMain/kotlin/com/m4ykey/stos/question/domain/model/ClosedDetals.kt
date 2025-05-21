package com.m4ykey.stos.question.domain.model

data class ClosedDetails(
    val reason : String,
    val description : String,
    val originalQuestions : List<OriginalQuestions>
)

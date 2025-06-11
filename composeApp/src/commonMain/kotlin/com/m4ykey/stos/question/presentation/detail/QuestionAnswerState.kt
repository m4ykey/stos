package com.m4ykey.stos.question.presentation.detail

import com.m4ykey.stos.question.domain.model.QuestionAnswer

data class QuestionAnswerState(
    val errorMessage : String? = null,
    val isLoading : Boolean = false,
    val answers : List<QuestionAnswer> = emptyList()
)

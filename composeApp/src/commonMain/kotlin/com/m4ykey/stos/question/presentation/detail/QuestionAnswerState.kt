package com.m4ykey.stos.question.presentation.detail

import com.m4ykey.stos.question.domain.model.Answer

data class QuestionAnswerState(
    val errorMessage : String? = null,
    val isLoading : Boolean = false,
    val answers : List<Answer> = emptyList()
)

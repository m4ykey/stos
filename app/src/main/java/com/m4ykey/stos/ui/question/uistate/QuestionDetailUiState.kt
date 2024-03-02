package com.m4ykey.stos.ui.question.uistate

import com.m4ykey.stos.data.domain.model.question.QuestionItem

data class QuestionDetailUiState(
    val isLoading : Boolean = false,
    val error : String? = null,
    val detail : QuestionItem? = null
)
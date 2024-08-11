package com.m4ykey.stos.ui.question.uistate

import com.m4ykey.network.data.model.QuestionDetail

data class QuestionDetailUiState(
    val isLoading : Boolean = false,
    val isError : String? = null,
    val questionDetail : QuestionDetail? = null
)

package com.m4ykey.stos.question.presentation.detail

import com.m4ykey.stos.question.domain.model.QuestionDetail

data class QuestionDetailState(
    val question : QuestionDetail? = null,
    val isLoading : Boolean = false,
    val errorMessage : String? = null,
    val selectedUserId : Int? = null,
    val selectedUrl : String? = null,
    val selectedTag : String? = null
)

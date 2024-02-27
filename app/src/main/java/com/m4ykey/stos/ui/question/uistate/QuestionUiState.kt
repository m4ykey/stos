package com.m4ykey.stos.ui.question.uistate

import androidx.paging.PagingData
import com.m4ykey.stos.data.domain.model.question.QuestionItem

data class  QuestionUiState(
    val isLoading : Boolean = false,
    val error : String? = null,
    val questionList : PagingData<QuestionItem>? = null
)

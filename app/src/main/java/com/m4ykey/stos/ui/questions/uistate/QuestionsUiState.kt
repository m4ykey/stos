package com.m4ykey.stos.ui.questions.uistate

import androidx.paging.PagingData
import com.m4ykey.stos.data.domain.model.question.QuestionItem

data class QuestionsUiState(
    val isLoading : Boolean = false,
    val error : String? = null,
    val questionList : PagingData<QuestionItem>? = null
)

package com.m4ykey.stos.ui.screen.question.uistate

import androidx.paging.PagingData
import com.m4ykey.network.data.model.Question
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class QuestionUiState(
    val isLoading : Boolean = false,
    val isError : String? = null,
    val questionList : Flow<PagingData<Question>> = emptyFlow()
)
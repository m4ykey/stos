package com.m4ykey.stos.ui.screen.question.uistate

import androidx.paging.PagingData
import com.m4ykey.network.data.model.Answer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class QuestionAnswerUiState(
    val isLoading : Boolean = false,
    val isError : String? = null,
    val questionAnswerList : Flow<PagingData<Answer>> = emptyFlow()
)

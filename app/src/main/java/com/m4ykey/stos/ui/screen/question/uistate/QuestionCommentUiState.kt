package com.m4ykey.stos.ui.screen.question.uistate

import androidx.paging.PagingData
import com.m4ykey.network.data.model.Comment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class QuestionCommentUiState(
    val isLoading : Boolean = false,
    val isError : String? = null,
    val commentList : Flow<PagingData<Comment>> = emptyFlow()
)
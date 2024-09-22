package com.m4ykey.stos.ui.search.uistate

import androidx.paging.PagingData
import com.m4ykey.network.data.model.Question
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class SearchUiState(
    val isLoading : Boolean = false,
    val isError : String? = null,
    val searchList : Flow<PagingData<Question>> = emptyFlow()
)
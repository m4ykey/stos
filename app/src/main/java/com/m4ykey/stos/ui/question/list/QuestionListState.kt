package com.m4ykey.stos.ui.question.list

import androidx.paging.PagingData
import com.m4ykey.stos.domain.models.questions.Question
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class QuestionListState(
    val isLoading : Boolean = true,
    val errorMessage : String? = null,
    val sort : QuestionSort = QuestionSort.HOT,
    val order : QuestionOrder = QuestionOrder.DESC,
    val questionResults : Flow<PagingData<Question>> = emptyFlow()
)

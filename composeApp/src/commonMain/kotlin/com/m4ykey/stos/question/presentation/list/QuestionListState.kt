package com.m4ykey.stos.question.presentation.list

import com.m4ykey.stos.question.domain.model.Question

data class QuestionListState(
    val questions : List<Question> = emptyList(),
    val currentPage : Int = 1,
    val sort : QuestionSort = QuestionSort.HOT,
    val order : QuestionOrder = QuestionOrder.DESC
)

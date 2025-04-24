package com.m4ykey.stos.question.presentation

import com.m4ykey.stos.question.domain.model.Question

sealed interface QuestionListAction {
    data class OnQuestionClick(val question : Question) : QuestionListAction
    data class OnSortClick(val sort : QuestionSort) : QuestionListAction
}
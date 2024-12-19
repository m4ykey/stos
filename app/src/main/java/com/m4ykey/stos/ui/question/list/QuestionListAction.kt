package com.m4ykey.stos.ui.question.list

import com.m4ykey.stos.domain.models.questions.Question

sealed interface QuestionListAction {
    data class OnQuestionClick(val question : Question) : QuestionListAction
    data class OnOrderClick(val order : QuestionOrder) : QuestionListAction
    data class OnSortClick(val sort : QuestionSort) : QuestionListAction
}
package com.m4ykey.stos.question.presentation

import com.m4ykey.stos.question.domain.model.Question

sealed interface QuestionListAction {
    sealed class OnQuestionClick(val question : Question) : QuestionListAction
}
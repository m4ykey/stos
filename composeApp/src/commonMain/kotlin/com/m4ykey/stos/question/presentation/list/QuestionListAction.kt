package com.m4ykey.stos.question.presentation.list

sealed interface QuestionListAction {
    data class OnQuestionClick(val questionId : Int) : QuestionListAction
    data class OnSortClick(val sort : QuestionSort) : QuestionListAction
    data class OnOwnerClick(val userId : Int) : QuestionListAction
}
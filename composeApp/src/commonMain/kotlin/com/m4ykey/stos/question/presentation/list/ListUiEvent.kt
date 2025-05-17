package com.m4ykey.stos.question.presentation.list

sealed interface ListUiEvent {
    data class NavigateToQuestion(val questionId : Int) : ListUiEvent
    data class NavigateToUser(val userId : Int) : ListUiEvent
    data class ChangeSort(val sort : QuestionSort) : ListUiEvent
}
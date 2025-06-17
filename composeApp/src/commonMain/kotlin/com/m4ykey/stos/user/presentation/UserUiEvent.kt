package com.m4ykey.stos.user.presentation

sealed interface UserUiEvent {
    data class NavigateToQuestion(val questionId : Int) : UserUiEvent
    data class OpenLink(val url : String) : UserUiEvent
}
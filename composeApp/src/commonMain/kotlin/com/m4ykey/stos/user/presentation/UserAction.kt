package com.m4ykey.stos.user.presentation

sealed interface UserAction {
    data class OnOpenLink(val link : String) : UserAction
    data class OnQuestionClick(val questionId : Int) : UserAction
}
package com.m4ykey.stos.question.presentation.detail

sealed interface QuestionDetailAction {
    data class OnTagClick(val tag : String) : QuestionDetailAction
    data class OnOwnerClick(val userId : Int) : QuestionDetailAction
    data class OnOpenLink(val link : String) : QuestionDetailAction
}
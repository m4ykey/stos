package com.m4ykey.stos.question.presentation.detail

sealed interface DetailUiEvent {
    data class OpenLink(val url : String) : DetailUiEvent
    data class NavigateToUser(val userId : Int) : DetailUiEvent
    data class NavigateToTag(val tag : String) : DetailUiEvent
}
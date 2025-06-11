package com.m4ykey.stos.search.presentation

import com.m4ykey.stos.question.presentation.list.QuestionSort

sealed interface SearchListAction {
    data class OnQuestionClick(val questionId : Int) : SearchListAction
    data class OnTagClick(val tag : String) : SearchListAction
    data class OnOwnerClick(val userId : Int) : SearchListAction
    data class OnSearchClick(val inTitle : String, val tag : String = "") : SearchListAction
    data class OnSortClick(val sort : QuestionSort) : SearchListAction
}
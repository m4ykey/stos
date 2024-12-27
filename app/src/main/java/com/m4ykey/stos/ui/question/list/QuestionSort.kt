package com.m4ykey.stos.ui.question.list

import com.m4ykey.stos.R

enum class QuestionSort(val displayNameResId : Int) {
    HOT(R.string.hot),
    ACTIVITY(R.string.activity),
    VOTES(R.string.votes),
    CREATION(R.string.creation),
    WEEK(R.string.week),
    MONTH(R.string.month)
}
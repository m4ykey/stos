package com.m4ykey.stos.question.presentation.components

actual fun getString(id: String): String {
    return when (id) {
        "days" -> "days"
        "hours" -> "hours"
        "min" -> "min"
        "sec" -> "sec"
        else -> ""
    }
}
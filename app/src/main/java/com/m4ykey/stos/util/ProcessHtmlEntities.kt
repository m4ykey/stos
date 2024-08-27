package com.m4ykey.stos.util

fun processHtmlEntities(text : String) : String {
    val htmlEntries = mapOf(
        "&quot;" to "\"",
        "&lt;" to "<",
        "&gt;" to ">",
        "&le;" to "≤",
        "&ge;" to "≥",
        "&amp;" to "&",
        "&#39;" to "'"
    )
    return htmlEntries.entries.fold(text) { acc, (entity, replacement) ->
        acc.replace(entity, replacement)
    }
}
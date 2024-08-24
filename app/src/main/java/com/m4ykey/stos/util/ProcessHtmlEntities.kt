package com.m4ykey.stos.util

fun processHtmlEntities(text : String) : String {
    return text
        .replace("&quot;", "\"")
        .replace("&lt;", "<")
        .replace("&gt;", ">")
        .replace("&le;", "≤")
        .replace("&ge;", "≥")
        .replace("&amp;", "&")
        .replace("&#39;", "'")
}
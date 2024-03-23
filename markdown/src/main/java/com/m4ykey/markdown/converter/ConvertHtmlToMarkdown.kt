package com.m4ykey.markdown.converter

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

fun convertHtmlToMarkdown(html : String) : String {
    val document : Document = Jsoup.parse(html)
    return convertToMarkdown(document)
}
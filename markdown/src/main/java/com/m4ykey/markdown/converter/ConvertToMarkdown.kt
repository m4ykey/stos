package com.m4ykey.markdown.converter

import com.m4ykey.markdown.MarkdownVisitor
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode

fun convertToMarkdown(document: Document) : String {
    val stringBuilder = StringBuilder()
    val visitor = MarkdownVisitor()
    document.body().childNodes().forEach { child ->
        when (child) {
            is TextNode -> visitor.visitTextNode(child, stringBuilder)
            is Element -> visitor.visitElementNode(child, stringBuilder)
        }
    }
    return stringBuilder.toString()
}
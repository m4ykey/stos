package com.m4ykey.stos.extensions

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode

private fun processElements(element: Element, stringBuilder: StringBuilder) {
    for (child in element.childNodes()) {
        when (child) {
            is TextNode -> stringBuilder.append(child.text())
            is Element -> {
                when (child.tagName()) {
                    "p" -> {
                        processElements(child, stringBuilder)
                        stringBuilder.append("\n\n")
                    }
                    "pre" -> {
                        val codeText = child.select("code").html()
                        stringBuilder.append("\n```\n")
                            .append(codeText)
                            .append("\n```\n")
                    }
                    "a" -> {
                        val imgTag = child.getElementsByTag("img").firstOrNull()
                        if (imgTag != null) {
                            val imageUrl = imgTag.attr("src")
                            val imageAlt = imgTag.attr("alt")
                            stringBuilder.append("![$imageAlt]($imageUrl)\n")
                        } else {
                            val linkText = child.text()
                            val linkUrl = child.attr("href")
                            stringBuilder.append("[$linkText]($linkUrl)\n")
                        }
                    }
                    "ul" -> {
                        processList(child, stringBuilder)
                        stringBuilder.append("\n")
                    }
                    "code" -> {
                        val codeText = child.html()
                        stringBuilder.append("`$codeText`")
                    }
                    "blockquote" -> {
                        stringBuilder.append("> ")
                        processElements(child, stringBuilder)
                        stringBuilder.append("\n")
                    }
                    "em", "strong" -> {
                        val tag = if (child.tagName() == "em") "_" else "**"
                        stringBuilder.append(tag)
                        processElements(child, stringBuilder)
                        stringBuilder.append(tag)
                    }
                    else -> processElements(child, stringBuilder)
                }
            }
        }
    }
}

private fun processList(element: Element, stringBuilder: StringBuilder) {
    element.children().forEach { child ->
        stringBuilder.append("- ")
            .append(child.text())
            .append("\n")
    }
}

fun convertToMarkdown(document : Document) : String {
    val stringBuilder = StringBuilder()
    processElements(document.body(), stringBuilder)
    return stringBuilder.toString()
}
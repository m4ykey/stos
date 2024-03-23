package com.m4ykey.markdown

import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode

class MarkdownVisitor : ElementVisitor {
    override fun visitTextNode(node: TextNode, stringBuilder: StringBuilder) {
        stringBuilder.append(node.text())
    }

    override fun visitElementNode(node: Element, stringBuilder: StringBuilder) {
        when (node.tagName()) {
            "p" -> {
                processParagraph(node, stringBuilder)
                stringBuilder.append("\n\n")
            }
            "pre" -> processPre(node, stringBuilder)
            "a" -> processLink(node, stringBuilder)
            "ul" -> {
                processList(node, stringBuilder)
                stringBuilder.append("\n")
            }
            "code" -> stringBuilder.append("`").append(node.html()).append("`")
            "blockquote" -> {
                stringBuilder.append("> ")
                processElement(node, stringBuilder)
                stringBuilder.append("\n")
            }
            "h1" -> stringBuilder.append("# ").append(node.text()).append("\n\n")
            "h2" -> stringBuilder.append("## ").append(node.text()).append("\n\n")
            "em" -> processTextWithSurroundingTag(node, stringBuilder, "_")
            "strong" -> processTextWithSurroundingTag(node, stringBuilder, "**")
            else -> processElement(node, stringBuilder)
        }
    }

    private fun processTextWithSurroundingTag(node: Element, stringBuilder: StringBuilder, tag : String) {
        stringBuilder.append(tag)
        processElement(node, stringBuilder)
        stringBuilder.append(tag)
    }

    private fun processLink(node: Element, stringBuilder: StringBuilder) {
        val imgTag = node.getElementsByTag("img").firstOrNull()
        if (imgTag != null) {
            val imageUrl = imgTag.attr("src")
            val imageAlt = imgTag.attr("alt")
            stringBuilder.append("![${imageAlt}](${imageUrl})\n")
        } else {
            val linkText = node.text()
            val linkUrl = node.attr("href")
            stringBuilder.append("[$linkText]($linkUrl)\n")
        }
    }

    private fun processPre(node: Element, stringBuilder: StringBuilder) {
        val codeText = node.select("code").html()
        stringBuilder.append("\n```\n").append(codeText).append("\n```\n")
    }

    private fun processList(node : Element, stringBuilder: StringBuilder) {
        node.children().forEach { child ->
            stringBuilder.append("- ").append(child.text()).append("\n")
        }
    }

    private fun processElement(element: Element, stringBuilder: StringBuilder) {
        element.childNodes().forEach { child ->
            when (child) {
                is TextNode -> visitTextNode(child, stringBuilder)
                is Element -> visitElementNode(child, stringBuilder)
            }
        }
    }

    private fun processParagraph(node : Element, stringBuilder: StringBuilder) {
        processElement(node, stringBuilder)
    }
}
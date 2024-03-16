package com.m4ykey.markdown

import android.widget.TextView
import io.noties.markwon.Markwon

fun TextView.toMarkdownText(markdown : String) {
    val markwon = Markwon.builder(context).build()
    val markdownRender = Markdown(markwon)
    markdownRender.setMarkdown(this, markdown)
}
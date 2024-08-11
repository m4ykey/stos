package com.m4ykey.stos.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun CustomMarkdown(
    modifier: Modifier = Modifier,
    markdown : String
) {
    val processedMarkdown = processHtmlEntities(markdown)

    MarkdownText(
        markdown = processedMarkdown,
        modifier = modifier,
        linkColor = Color(0xFF2367DD)
    )
}

private fun processHtmlEntities(text : String) : String {
    return text
        .replace("&quot;", "\"")
        .replace("&lt;", "<")
        .replace("&gt;", ">")
        .replace("&le;", "≤")
        .replace("&ge;", "≥")
        .replace("&amp;", "&")
        .replace("&#39;", "'")
}
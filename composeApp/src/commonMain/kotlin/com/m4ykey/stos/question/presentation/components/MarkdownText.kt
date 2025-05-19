package com.m4ykey.stos.question.presentation.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.m4ykey.stos.core.data.htmlDecode
import com.mikepenz.markdown.coil3.Coil3ImageTransformerImpl
import com.mikepenz.markdown.compose.components.markdownComponents
import com.mikepenz.markdown.compose.elements.MarkdownHighlightedCodeBlock
import com.mikepenz.markdown.compose.elements.MarkdownHighlightedCodeFence
import com.mikepenz.markdown.m3.Markdown
import com.mikepenz.markdown.model.MarkdownTypography
import dev.snipme.highlights.Highlights
import dev.snipme.highlights.model.SyntaxThemes

@Composable
fun MarkdownText(
    content : String,
    modifier : Modifier = Modifier,
    fontSize : TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal
) {
    val isDarkTheme = isSystemInDarkTheme()
    val highlightsBuilder = remember(isDarkTheme) {
        Highlights.Builder().theme(SyntaxThemes.atom(darkMode = isDarkTheme))
    }

    val customTypography = remember(fontSize, fontWeight) {
        object : MarkdownTypography {
            override val h1 = TextStyle(fontSize = fontSize * 1.6f, fontWeight = fontWeight)
            override val h2 = TextStyle(fontSize = fontSize * 1.4f, fontWeight = fontWeight)
            override val h3 = TextStyle(fontSize = fontSize * 1.2f, fontWeight = fontWeight)
            override val h4 = TextStyle(fontSize = fontSize * 1.1f, fontWeight = fontWeight)
            override val h5 = TextStyle(fontSize = fontSize, fontWeight = fontWeight)
            override val h6 = TextStyle(fontSize = fontSize * 0.9f, fontWeight = fontWeight)
            override val paragraph = TextStyle(fontSize = fontSize, fontWeight = fontWeight)
            override val text = paragraph
            override val bullet = paragraph
            override val list = paragraph
            override val ordered = paragraph
            override val quote = TextStyle(fontSize = fontSize, fontStyle = FontStyle.Italic)
            override val code = TextStyle(fontSize = fontSize * 0.9f, fontFamily = FontFamily.Monospace)
            override val inlineCode = code
            override val table = paragraph
            override val link = TextStyle(color = Color(0xFF1E88E5))
            override val textLink = TextLinkStyles(
                style = SpanStyle(color = Color(0xFF1E88E5), textDecoration = TextDecoration.Underline)
            )
        }
    }

    Box(modifier = modifier.fillMaxWidth()) {
        Markdown(
            modifier = modifier.fillMaxWidth(),
            content = content.htmlDecode().trimIndent(),
            imageTransformer = Coil3ImageTransformerImpl,
            typography = customTypography,
            components = markdownComponents(
                codeBlock = {
                    MarkdownHighlightedCodeBlock(
                        content = it.content,
                        node = it.node,
                        highlights = highlightsBuilder
                    )
                },
                codeFence = {
                    MarkdownHighlightedCodeFence(
                        content = it.content,
                        node = it.node,
                        highlights = highlightsBuilder
                    )
                }
            )
        )
    }
}
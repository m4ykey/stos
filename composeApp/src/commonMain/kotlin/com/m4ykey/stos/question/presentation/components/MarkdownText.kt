package com.m4ykey.stos.question.presentation.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.m4ykey.stos.core.data.htmlDecode
import com.mikepenz.markdown.coil3.Coil3ImageTransformerImpl
import com.mikepenz.markdown.compose.components.markdownComponents
import com.mikepenz.markdown.compose.elements.MarkdownHighlightedCodeBlock
import com.mikepenz.markdown.compose.elements.MarkdownHighlightedCodeFence
import com.mikepenz.markdown.m3.Markdown
import com.mikepenz.markdown.m3.markdownTypography
import com.mikepenz.markdown.model.MarkdownTypography
import dev.snipme.highlights.Highlights
import dev.snipme.highlights.model.SyntaxThemes

@Composable
fun MarkdownText(
    content : String,
    typography: MarkdownTypography = markdownTypography()
) {
    val isDarkTheme = isSystemInDarkTheme()
    val highlightsBuilder = remember(isDarkTheme) {
        Highlights.Builder().theme(SyntaxThemes.atom(darkMode = isDarkTheme))
    }

    Markdown(
        content = content.htmlDecode().trimIndent(),
        imageTransformer = Coil3ImageTransformerImpl,
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
        ),
        typography = typography
    )
}
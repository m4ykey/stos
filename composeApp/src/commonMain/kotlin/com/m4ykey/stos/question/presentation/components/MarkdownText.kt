package com.m4ykey.stos.question.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.m4ykey.stos.core.data.htmlDecode
import com.mikepenz.markdown.annotator.annotatorSettings
import com.mikepenz.markdown.annotator.buildMarkdownAnnotatedString
import com.mikepenz.markdown.compose.components.MarkdownComponentModel
import com.mikepenz.markdown.compose.components.markdownComponents
import com.mikepenz.markdown.compose.elements.MarkdownHighlightedCodeBlock
import com.mikepenz.markdown.compose.elements.MarkdownHighlightedCodeFence
import com.mikepenz.markdown.compose.elements.MarkdownTable
import com.mikepenz.markdown.compose.elements.material.MarkdownBasicText
import com.mikepenz.markdown.m3.Markdown
import com.mikepenz.markdown.model.ImageData
import com.mikepenz.markdown.model.ImageTransformer
import com.mikepenz.markdown.model.MarkdownTypography
import dev.snipme.highlights.Highlights
import dev.snipme.highlights.model.SyntaxThemes

val coil3ImageTransfer = object : ImageTransformer {
    @Composable
    override fun transform(link: String): ImageData? {
        val cleanUrl = remember(link) {
            link.trim().let { url ->
                when {
                    url.isBlank() -> null
                    url.startsWith("//") -> "https:$url"
                    url.startsWith("/") -> null
                    !url.startsWith("http://") && !url.startsWith("https://") -> "https://$url"
                    else -> url
                }
            }
        }

        if (cleanUrl == null) return null

        var imageState by remember { mutableStateOf<AsyncImagePainter.State>(AsyncImagePainter.State.Empty) }

        val cachePolicy = CachePolicy.ENABLED

        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalPlatformContext.current)
                .data(cleanUrl)
                .crossfade(true)
                .diskCachePolicy(cachePolicy)
                .memoryCachePolicy(cachePolicy)
                .networkCachePolicy(cachePolicy)
                .build(),
            onState = { state ->
                imageState = state
            }
        )

        return when (imageState) {
            is AsyncImagePainter.State.Error -> {
                null
            }
            is AsyncImagePainter.State.Success -> {
                ImageData(
                    contentDescription = "Image from $cleanUrl",
                    painter = painter,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                )
            }
            is AsyncImagePainter.State.Loading -> {
                ImageData(
                    contentDescription = "Loading image",
                    painter = painter,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .shimmerEffect()
                )
            }
            else -> {
                ImageData(
                    contentDescription = "Image from $cleanUrl",
                    painter = painter,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                )
            }
        }
    }
}

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

    val table : @Composable (MarkdownComponentModel) -> Unit = { model ->
        MarkdownTable(
            content = model.content,
            node = model.node,
            style = model.typography.table,
            headerBlock = { content, header, tableWidth, style ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (isDarkTheme) Color(0xFF333333) else Color(0xFFEEEEEE)
                        )
                        .padding(12.dp)
                ) {
                    val headerText = content.buildMarkdownAnnotatedString(
                        textNode = header,
                        annotatorSettings = annotatorSettings(),
                        style = style.copy(fontWeight = FontWeight.Bold)
                    ).text

                    val cells = headerText.split("|").filter { it.trim().isNotEmpty() }
                    cells.forEach { cellText ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = cellText.trim(),
                                overflow = TextOverflow.Visible,
                                maxLines = Int.MAX_VALUE,
                                style = style.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
            },
            rowBlock = { content, row, tableWidth, style ->
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        val rowText = content.buildMarkdownAnnotatedString(
                            textNode = row,
                            style = style,
                            annotatorSettings = annotatorSettings()
                        ).text

                        val cells = rowText.split("|").filter { it.trim().isNotEmpty() }
                        cells.forEach { cellText ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    style = style,
                                    overflow = TextOverflow.Visible,
                                    maxLines = Int.MAX_VALUE,
                                    text = cellText.trim()
                                )
                            }
                        }
                    }
                    HorizontalDivider(
                        color = if (isDarkTheme) Color(0xFF444444) else Color(0xFFE0E0E0),
                        thickness = 0.5.dp
                    )
                }
            }
        )
    }

    val customComponents = markdownComponents(
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
        },
        table = table
    )

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
            imageTransformer = coil3ImageTransfer,
            typography = customTypography,
            components = customComponents
        )
    }
}
package com.m4ykey.stos.markdown

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.m4ykey.stos.core.data.htmlDecode

fun preprocessMarkdown(text : String) : List<MarkdownNode> {
    return parseMarkdown(text.htmlDecode().trimIndent())
}

@Composable
fun MarkdownText(
    modifier: Modifier = Modifier,
    text : String,
    fontWeight: FontWeight = FontWeight.Normal,
    fontSize : TextUnit = TextUnit.Unspecified
) {
    val nodes = remember(text) { preprocessMarkdown(text) }

    Column(modifier = modifier) {
        var inlineGroup = mutableListOf<MarkdownNode>()

        for (node in nodes) {
            when (node) {
                is MarkdownNode.Text, is MarkdownNode.Bold -> inlineGroup.add(node)
                is MarkdownNode.Heading -> {
                    if (inlineGroup.isNotEmpty()) {
                        RenderInlineNodes(inlineGroup, fontWeight, fontSize)
                        inlineGroup = mutableListOf()
                    }
                    RenderNode(node)
                }
            }
        }

        if (inlineGroup.isNotEmpty()) {
            RenderInlineNodes(inlineGroup, fontWeight, fontSize)
        }
    }
}

@Composable
fun RenderNode(node : MarkdownNode) {
    when (node) {
        is MarkdownNode.Text -> {
            Text(text = node.text)
        }
        is MarkdownNode.Bold -> {
            Text(
                text = node.text.joinToString("") { (it as? MarkdownNode.Text)?.text ?: "" },
                fontWeight = FontWeight.Bold
            )
        }
        is MarkdownNode.Heading -> {
            val fontSize = when (node.level) {
                1 -> 32.sp
                2 -> 24.sp
                3 -> 20.sp
                else -> 18.sp
            }

            val text = node.text.joinToString("") { (it as? MarkdownNode.Text)?.text ?: "" }

            Text(
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp),
                text = text
            )
        }
    }
}

@Composable
fun RenderInlineNodes(
    nodes : List<MarkdownNode>,
    fontWeight: FontWeight,
    fontSize: TextUnit
) {
    val annotatedString = buildAnnotatedString {
        nodes.forEach { node ->
            when (node) {
                is MarkdownNode.Text -> append(node.text)
                is MarkdownNode.Bold -> {
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        node.text.forEach { inner ->
                            if (inner is MarkdownNode.Text) append(inner.text)
                        }
                    }
                }
                else -> {}
            }
        }
    }

    Text(
        text = annotatedString,
        fontSize = fontSize,
        fontWeight = fontWeight
    )
}
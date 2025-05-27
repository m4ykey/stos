package com.m4ykey.stos.markdown

fun parseMarkdown(text : String) : List<MarkdownNode> {
    val lines = text.lines()
    val nodes = mutableListOf<MarkdownNode>()

    for (line in lines) {
        if (line.startsWith("#")) {
            val level = line.takeWhile { it == '#' }.length
            val contentText = line.drop(level).trim()

            nodes.add(
                MarkdownNode.Heading(
                    text = listOf(MarkdownNode.Text(contentText)),
                    level = level
                )
            )
        } else {
            nodes.addAll(parseInlineMarkdown(line))
        }
    }

    return nodes
}

fun parseInlineMarkdown(line : String) : List<MarkdownNode> {
    val nodes = mutableListOf<MarkdownNode>()
    var remaining = line

    while (true) {
        val start = remaining.indexOf("**")
        if (start == -1) {
            if (remaining.isNotEmpty()) {
                nodes.add(MarkdownNode.Text(remaining))
            }
            break
        }

        val end = remaining.indexOf("**", start + 2)
        if (end == -1) {
            nodes.add(MarkdownNode.Text(remaining))
            break
        }

        val before = remaining.substring(0, start)
        val boldContent = remaining.substring(start + 2, end)
        remaining = remaining.substring(end + 2)

        if (before.isNotEmpty()) {
            nodes.add(MarkdownNode.Text(before))
        }

        nodes.add(MarkdownNode.Bold(listOf(MarkdownNode.Text(boldContent))))
    }

    return nodes
}
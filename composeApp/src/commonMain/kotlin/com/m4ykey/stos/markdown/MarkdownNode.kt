package com.m4ykey.stos.markdown

sealed class MarkdownNode {
    data class Text(val text : String) : MarkdownNode()
    data class Bold(val text : List<MarkdownNode>) : MarkdownNode()
    data class Heading(val text : List<MarkdownNode>, val level : Int) : MarkdownNode()
}
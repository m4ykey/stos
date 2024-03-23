package com.m4ykey.markdown

import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode
import java.lang.StringBuilder

interface ElementVisitor {
    fun visitTextNode(node: TextNode, stringBuilder: StringBuilder)
    fun visitElementNode(node : Element, stringBuilder: StringBuilder)
}
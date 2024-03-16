package com.m4ykey.markdown

import android.text.Spanned
import org.commonmark.node.Node

interface RenderText {
    fun render(node : Node) : Spanned
}
package com.m4ykey.markdown.converter

import android.widget.TextView
import io.noties.markwon.Markwon
import io.noties.markwon.image.coil.CoilImagesPlugin

fun displayMarkdownInTextView(markdown : String, textView: TextView) {
    val markwon = Markwon.builder(textView.context)
        .usePlugin(CoilImagesPlugin.create(textView.context))
        .build()
    markwon.setMarkdown(textView, markdown)
}
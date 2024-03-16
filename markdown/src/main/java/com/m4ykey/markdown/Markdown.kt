package com.m4ykey.markdown

import android.content.Context
import android.widget.TextView
import io.noties.markwon.Markwon
import io.noties.markwon.html.HtmlPlugin
import io.noties.markwon.image.coil.CoilImagesPlugin

fun setMarkdown(textView : TextView, text : String, context: Context) {

    val markwon = Markwon.builder(context)
        .usePlugin(HtmlPlugin.create())
        .usePlugin(CoilImagesPlugin.create(context))
        .build()

    val formattedText = text.replace("\n", "<br>")
    markwon.setMarkdown(textView, formattedText)
}

fun formatTitle(textView: TextView, text : String, context: Context) {
    val markwon = Markwon.builder(context).build()
    markwon.setMarkdown(textView, text)
}
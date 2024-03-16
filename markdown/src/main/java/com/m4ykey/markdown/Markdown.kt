package com.m4ykey.markdown

import android.text.Spanned
import android.widget.TextView
import io.noties.markwon.Markwon
import io.noties.prism4j.annotations.PrismBundle
import org.commonmark.node.Node
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@PrismBundle(includeAll = true)
class Markdown @Inject constructor(private val markwon: Markwon) : RenderText {

    override fun render(node: Node): Spanned {
        return markwon.render(node)
    }

    fun setMarkdown(textView: TextView, markdown : String) {
        markwon.setMarkdown(textView, markdown.removeSpecials())
    }

    fun setParsedMarkdown(textView: TextView, spanned : Spanned) {
        markwon.setParsedMarkdown(textView, spanned)
    }

}
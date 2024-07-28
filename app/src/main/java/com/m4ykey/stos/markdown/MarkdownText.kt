package com.m4ykey.stos.markdown

import android.text.Html
import android.text.Spanned
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit

@Composable
fun MarkdownText(
    modifier: Modifier = Modifier,
    text : String,
    fontSize : TextUnit = TextUnit.Unspecified,
    fontFamily: FontFamily = FontFamily.Default
) {
    val context = LocalContext.current
    val markwon = createMarkwon(context)

    val markdown = markwon.toMarkdown(text)

    val spanned : Spanned = Html.fromHtml(markdown.toString(), Html.FROM_HTML_MODE_LEGACY)

    Text(
        text = spanned.toString(),
        fontSize = fontSize,
        modifier = modifier,
        fontFamily = fontFamily
    )
}
package com.m4ykey.stos.util

import android.content.Context
import android.content.Intent

fun shareUrl(link : String, context : Context) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, link)
        type = "text/plain"
    }
    context.startActivity(Intent.createChooser(intent, link))
}
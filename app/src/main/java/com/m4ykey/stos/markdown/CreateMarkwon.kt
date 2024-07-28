package com.m4ykey.stos.markdown

import android.content.Context
import io.noties.markwon.Markwon
import io.noties.markwon.core.CorePlugin
import io.noties.markwon.image.coil.CoilImagesPlugin

fun createMarkwon(context : Context) : Markwon {
    return Markwon.builder(context)
        .usePlugin(CorePlugin.create())
        .usePlugin(CoilImagesPlugin.create(context))
        .build()
}
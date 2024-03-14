package com.m4ykey.stos.extensions

import android.widget.ImageView
import coil.load

fun ImageView.loadImage(item : String) {
    load(item) {
        crossfade(true)
        crossfade(500)
    }
}
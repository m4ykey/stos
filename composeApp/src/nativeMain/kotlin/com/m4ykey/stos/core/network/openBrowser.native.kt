package com.m4ykey.stos.core.network

import kotlinx.io.IOException

actual fun openBrowser(url: String) {
    try {
        val process = ProcessBuilder("open", url)
        process.start()
    } catch (e : IOException) {
        e.printStackTrace()
    }
}
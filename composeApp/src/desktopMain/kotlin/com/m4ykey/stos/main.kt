package com.m4ykey.stos

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.m4ykey.stos.app.App
import com.m4ykey.stos.di.initModules

fun main() {
    initModules()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "stos",
        ) {
            App()
        }
    }
}
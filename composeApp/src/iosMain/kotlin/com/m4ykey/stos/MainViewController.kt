package com.m4ykey.stos

import androidx.compose.ui.window.ComposeUIViewController
import com.m4ykey.stos.di.initModules

fun MainViewController() = ComposeUIViewController(
    configure = {
        initModules()
    }
) { App() }
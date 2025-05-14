package com.m4ykey.stos.core.network

import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import com.m4ykey.stos.app.StosApplication

actual fun openBrowser(url: String) {
    val context = StosApplication.getContext()
    val customTabIntents = CustomTabsIntent.Builder().build()
    customTabIntents.launchUrl(context, url.toUri())
}
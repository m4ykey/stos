package com.m4ykey.stos.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.m4ykey.stos.app.navigation.AppNavHost
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {

        val navHostController = rememberNavController()

        AppNavHost(navHostController = navHostController)
    }
}
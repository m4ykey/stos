package com.m4ykey.stos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.m4ykey.stos.ui.navigation.AppNavigation
import com.m4ykey.stos.ui.theme.StosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StosTheme {
                val navController : NavHostController = rememberNavController()
                AppNavigation(navHostController = navController)
            }
        }
    }
}
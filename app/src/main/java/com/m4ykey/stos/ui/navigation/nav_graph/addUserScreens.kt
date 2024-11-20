package com.m4ykey.stos.ui.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.m4ykey.stos.ui.navigation.Screen
import com.m4ykey.stos.ui.screen.user.UserScreen

fun NavGraphBuilder.addUserScreens(navController : NavHostController) {
    composable(route = Screen.UserScreen.route) {
        UserScreen(
            onNavigateBack = { navController.navigateUp() }
        )
    }
}
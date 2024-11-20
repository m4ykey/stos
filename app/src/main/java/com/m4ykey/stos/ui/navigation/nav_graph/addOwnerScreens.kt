package com.m4ykey.stos.ui.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.m4ykey.stos.ui.navigation.Screen
import com.m4ykey.stos.ui.screen.owner.OwnerScreen

fun NavGraphBuilder.addOwnerScreens(navController : NavHostController) {
    composable(
        route = Screen.OwnerScreen.route,
        arguments = listOf(navArgument(Screen.OwnerScreen.argument) {
            type = NavType.IntType
        })
    ) { backStackEntry ->
        val ownerId = backStackEntry.arguments?.getInt(Screen.OwnerScreen.argument) ?: -1
        OwnerScreen(
            onNavigateBack = { navController.navigateUp() },
            ownerId = ownerId,
            onQuestionClick = { questionId ->
                navController.navigate(Screen.QuestionDetail.createRoute(questionId))
            }
        )
    }
}